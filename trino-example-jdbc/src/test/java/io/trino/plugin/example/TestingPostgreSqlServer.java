/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.plugin.example;

import io.trino.testing.containers.junit.ReportLeakedContainers;
import org.intellij.lang.annotations.Language;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.testcontainers.containers.PostgreSQLContainer.POSTGRESQL_PORT;

public class TestingPostgreSqlServer
        implements Closeable
{
    private static final String USER = "test";
    private static final String PASSWORD = "test";
    private static final String DATABASE = "tpch";

    private final PostgreSQLContainer<?> dockerContainer;

    public TestingPostgreSqlServer()
    {
        // Use the oldest supported PostgreSQL version
        dockerContainer = new PostgreSQLContainer<>("postgres:latest")
                .withStartupAttempts(3)
                .withDatabaseName(DATABASE)
                .withUsername(USER)
                .withPassword(PASSWORD)
                .withCommand("postgres", "-c", "log_destination=stderr", "-c", "log_statement=all");
        dockerContainer.withCreateContainerCmdModifier(cmd -> cmd
                .withHostConfig(requireNonNull(cmd.getHostConfig(), "hostConfig is null")
                        .withPublishAllPorts(true)));
        dockerContainer.start();
        ReportLeakedContainers.ignoreContainerId(dockerContainer.getContainerId());

        execute("CREATE TABLE test (key INT, value VARCHAR)");
        execute("INSERT INTO test (key, value) VALUES (1, 'one'), (2, 'two')");
        execute("CREATE SCHEMA tpch");
    }

    public void execute(@Language("SQL") String sql)
    {
        execute(getJdbcUrl(), getProperties(), sql);
    }

    private static void execute(String url, Properties properties, String sql)
    {
        try (Connection connection = DriverManager.getConnection(url, properties);
                Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUser()
    {
        return USER;
    }

    public String getPassword()
    {
        return PASSWORD;
    }

    public Properties getProperties()
    {
        Properties properties = new Properties();
        properties.setProperty("user", USER);
        properties.setProperty("password", PASSWORD);
        properties.setProperty("currentSchema", "tpch,public");
        return properties;
    }

    public String getJdbcUrl()
    {
        return format("jdbc:postgresql://%s:%s/%s", dockerContainer.getHost(), dockerContainer.getMappedPort(POSTGRESQL_PORT), DATABASE);
    }

    @Override
    public void close()
    {
        dockerContainer.close();
    }
}

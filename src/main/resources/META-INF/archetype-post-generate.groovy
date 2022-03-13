// Maven Archetype isn't respecting the executable perm - use this post-install script to reset this

def rootDir = new File(request.getOutputDirectory() + "/" + request.getArtifactId())
def files = [
    new File(rootDir, "docker-build.sh"),
    new File(rootDir, "mvnw")
]
files.each {
    it.setExecutable(true, false);
}

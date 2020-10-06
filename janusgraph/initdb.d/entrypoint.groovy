:remote connect tinkerpop.server conf/remote.yaml session
:remote console

configurations = ConfiguredGraphFactory.getConfigurations()

if(configurations.size() == 0) {
    ConfiguredGraphFactory.create("default_graph");
}

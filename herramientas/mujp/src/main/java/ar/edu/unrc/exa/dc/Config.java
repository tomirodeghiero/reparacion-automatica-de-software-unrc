package ar.edu.unrc.exa.dc;

import java.io.File;
import java.nio.file.Paths;
import java.util.logging.Logger;

import ar.edu.unrc.exa.dc.logging.Logging;
import io.github.cdimascio.dotenv.Dotenv;

public class Config {

    enum Property {
        SUT_SOURCE_ROOT {
            @Override 
            public String getProperty() {
                return "mujp.sut.src";
            }

            @Override
            public String getDescription() {
                return "The source root folder of the sotware under test, e.g.: /home/gaia/my_super_project/src";
            }
        },
        TYPE_CHECKING_ALLOW_WRAPPERS {
            @Override
            public String getProperty() {
                return "mujp.type.allow_wrappers";
            }

            @Override
            public String getDescription() {
                return "If wrappers are allowed when checking if two types are compatible, e.g.: int is compatible with Integer or not";
            }
        };
        public abstract String getProperty();
        public abstract String getDescription();
    }

    private static final Logger logger = Logging.getLogger(Config.class, Logging.LoggingLevel.FINE, Logging.LoggingLevel.OFF);

    private final String CUSTOM_FILE_ARG = "pfile";
    private final String DEFAULT_PROPERTY_FILE = "settings.env";

    private final Dotenv dotenv;
    private static Config instance = null;

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    private Config() {
        String pfile = System.getProperty(CUSTOM_FILE_ARG);
        boolean useDefaultConfig = true;
        if (pfile == null) {
            logger.info(String.format("Loading settings from %s", DEFAULT_PROPERTY_FILE));
        } else {
            File pfileAsFile = Paths.get(pfile).toFile();
            if (!pfileAsFile.exists()) {
                logger.warning(String.format("File %s does not exists, defaulting to %s", pfileAsFile.toString(), DEFAULT_PROPERTY_FILE));
            } else if (!pfile.endsWith(".env")) {
                logger.warning(String.format("File %s does not have proper extension (.env), defaulting to %s", pfileAsFile.toString(), DEFAULT_PROPERTY_FILE));
            } else if (!pfileAsFile.isFile()) {
                logger.warning(String.format("%s is not a file, defaulting to %s", pfileAsFile.toString(), DEFAULT_PROPERTY_FILE));
            } else if (!pfileAsFile.canRead()) {
                logger.warning(String.format("No read permissions for file %s, defaulting to %s", pfileAsFile.toString(), DEFAULT_PROPERTY_FILE));
            } else {
                logger.info(String.format("Loading settings from %s", pfileAsFile.toString()));
                useDefaultConfig = false;
            }
        }
        dotenv = Dotenv.configure()
                        .filename(useDefaultConfig?DEFAULT_PROPERTY_FILE:pfile)
                        .load();
    }

    public String sourceRootFolder() {
        String root = dotenv.get(Property.SUT_SOURCE_ROOT.getProperty(), null);
        if (root == null) {
            logger.warning(String.format("No value set for %s, some features may fail", Property.SUT_SOURCE_ROOT.getProperty()));
        }
        return root;
    }

    public boolean allowPrimitiveWrapping() {
        String allowRaw = dotenv.get(Property.TYPE_CHECKING_ALLOW_WRAPPERS.getProperty(), "false");
        logger.info(
            String.format(
                "Got %s for %s (default value is false)",
                Property.TYPE_CHECKING_ALLOW_WRAPPERS.getProperty(),
                allowRaw
            )
        );
        return Boolean.parseBoolean(allowRaw);
    }
    
}

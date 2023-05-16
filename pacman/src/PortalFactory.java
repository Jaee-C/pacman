package src;

public class PortalFactory {
    private static PortalFactory instance;

    private PortalFactory() {
        // Private constructor for Singleton
    }

    public static PortalFactory getInstance() {
        if (instance == null) {
            instance = new PortalFactory();
        }
        return instance;
    }

    public Portal createPortal(Game game, PortalColour colour) {
        // Check if portal already has a pair
        Portal portal = new Portal(game, colour);
        return portal;
    }
}

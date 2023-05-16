package src;

public enum PortalColour {
    WHITE,
    YELLOW,
    DARK_GOLD,
    DARK_GREY;

    public String getImageName() {
        switch (this) {
            case WHITE: return "i_portalWhiteTile.png";
            case YELLOW: return "j_portalYellowTile.png";
            case DARK_GOLD: return "k_portalDarkGoldTile.png";
            case DARK_GREY: return "l_portalDarkGrayTile.png";
            default: {
                // Invalid Portal Colour
                assert false;
            }
        }
        return null;
    }
}
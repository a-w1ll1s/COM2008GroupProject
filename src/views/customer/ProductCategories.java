package views.customer;

public interface ProductCategories {
    public static final String TRAIN_SETS = "Train Sets";
    public static final String TRACK = "Track";
    public static final String TRACK_PACKS = "Track Packs";
    public static final String ROLLING_STOCK = "Rolling Stock";
    public static final String LOCOMOTIVES = "Locomotives";
    public static final String CONTROLLERS = "Controllers";

    public static final String[] categories = {
        LOCOMOTIVES,
        TRACK,
        ROLLING_STOCK,        
        CONTROLLERS,
        TRAIN_SETS,
        TRACK_PACKS
    };
}

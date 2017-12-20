package software.mybeans.models;

public class Tube {
    private String tubeName;
    public Tube(){
        tubeName = "default";
    }
    public Tube(String tubeName){
        this.tubeName = tubeName;
    }

    public String getTubeName() {
        return tubeName;
    }
}

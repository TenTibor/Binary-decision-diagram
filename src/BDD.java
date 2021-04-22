import java.util.Arrays;

public class BDD {
    String pattern;

    public BDD(String pattern) {
        this.pattern = pattern;
        BDD_create();
    }

    void BDD_create() {
        String[] splittedPattern = splitText(pattern);
        System.out.println(Arrays.toString(splittedPattern));
    }

    private String[] splitText(String text) {
        int mid = text.length() / 2;
        return new String[]{text.substring(0, mid), text.substring(mid)};
    }
}

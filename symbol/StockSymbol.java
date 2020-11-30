package symbol;

public class StockSymbol {
    public boolean isValidSymbol(String word) {
        for (Character ch: word.toCharArray()) {
            if (!Character.isUpperCase(ch) || !Character.isLetter(ch)) {
                return false;
            }
        }
        return  word.length() >= 1 && word.length() <= 4;
    }
}


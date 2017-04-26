package filling;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {

    private static Random random = new Random();

    private static String[] countries;

    static {
        String countriesString = "Angola , Argentina , Aruba , Australia , Austria , Azerbaijan , Bahamas , Bahrain , Belgium , Benin , Bolivia , Bonaire , Bosnia-Herzegovina , Botswana , Brazil , Brunei , Bulgaria , Cambodia , Cameroon , Canada , Chile , China (Fuzhou) , China (Shanghai) , China (Zhongshan) , Colombia , Costa Rica , Croatia , Curacao , Cyprus , Czech Republic , Democratic Republic of Congo , Denmark , East Africa , Ecuador , El Salvador , El Salvador-San Miguel , Estonia , Fiji , Finland , France , Gabon , Germany , Ghana , Great Britain , Greece , Guadaloupe , Guatemala , Honduras , Honduras-Tegucigalpa , Hong Kong , Hungary , Iceland , India , Indonesia , Ireland , Israel , Italy , Ivory Coast , Japan , Kazakhstan , Korea , Kosovo , Kuwait , Latvia , Lesotho , Lithuania , Luxembourg , Macau , Macedonia , Malaysia , Martinique , Mauritania , Mexico , Moldova , Namibia , Netherlands , New Zealand , Nicaragua , Nigeria , Northern Ireland , Norway , Panama, Paraguay, Peru, Philippines, Poland, Portugal, Puerto Rico, Qatar, Republic of Congo, Reunion Islands, Romania, Russia, Rwanda, Saba, Samoa, Saudi Arabia, Senegal, Serbia & Montenegro, Singapore, Slovak Republic, Slovenia, South Africa, Spain, St. Eustatius, St. Maarten, Suriname, Swaziland, Sweden, Switzerland, Taiwan, Tanzania, Thailand, Togo, Turkey, Uganda, Ukraine, United Arab Emirates, United States, Uruguay, Venezuela, Vietnam";
        countries = countriesString.split(", ");
        for (String country : countries) country.trim();
    }

    public static String generateName() {
        int length = random.nextInt(5) + 10;

        char[] chars = new char[length];
        String alphabet = "abcdefghijklmnopqrstuvwxyz   ";

        chars[0] = new String(new char[]{alphabet.charAt(random.nextInt(alphabet.length() - 3))}).toUpperCase().charAt(0);

        for (int i = 1; i < length; i++) {
            chars[i] = alphabet.charAt(random.nextInt(alphabet.length()));
            while (chars[i - 1] == ' ' && chars[i] == ' ')
                chars[i] = alphabet.charAt(random.nextInt(alphabet.length()));
            if (chars[i - 1] == ' ')
                chars[i] = new String(new char[]{chars[i]}).toUpperCase().charAt(0);
        }

        return new String(chars).trim();
    }

    public static String generateText(int length) {
        char[] chars = new char[length];
        String alphabet = "abcdefghijklmnopqrstuvwxyz   ";

        chars[0] = new String(new char[]{alphabet.charAt(random.nextInt(alphabet.length() - 3))}).toUpperCase().charAt(0);

        for (int i = 1; i < length; i++) {
            chars[i] = alphabet.charAt(random.nextInt(alphabet.length()));
        }

        return new String(chars).trim();
    }

    public static Date generateBirthday() {
        long minDate = -2208999600000L;
        long maxDate = 1483218000000L;
        long dayDurationInMillis = 86400000L;
        return new Date(Math.round(ThreadLocalRandom.current().nextLong(minDate, maxDate) / dayDurationInMillis) * dayDurationInMillis);
    }

    public static Date generateDate() {
        long minDate = 946674000000L;
        long maxDate = 1483218000000L;
        return new Date(ThreadLocalRandom.current().nextLong(minDate, maxDate));
    }

    public static Integer generateYear() {
        return random.nextInt(100) + 1918;
    }

    public static Integer generateDuration() {
        return random.nextInt(200) + 50;
    }

    public static Double generateRating() {
        return Math.round((random.nextInt(10) + random.nextDouble()) * 100) / 100.0;
    }

    public static List<String> generateGenres() {
        List<String> result = new ArrayList<>();
        List<Integer> resultIds = new ArrayList<>();
        String[] genres = new String[]{"Animation", "Drama", "Action", "Adventure", "Detective", "Fantasy", "Horror", "Science-fiction", "Thriller", "Western", "Comedy", "Documentary", "Sports"};

        resultIds.add(random.nextInt(genres.length));
        int index = random.nextInt(genres.length);
        while (index == resultIds.get(0))
            index = random.nextInt(genres.length);
        resultIds.add(index);
        index = random.nextInt(genres.length);
        while (index == resultIds.get(0) || index == resultIds.get(1))
            index = random.nextInt(genres.length);
        resultIds.add(index);

        for (Integer resultId : resultIds) result.add(genres[resultId]);

        return result;
    }

    public static String generateCountry() {
        return countries[random.nextInt(countries.length)].trim();
    }

}

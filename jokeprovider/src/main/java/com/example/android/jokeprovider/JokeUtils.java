package com.example.android.jokeprovider;

import java.util.ArrayList;
import java.util.List;

public class JokeUtils {

    private static final String CATEGORY_MATH = "math";
    private static final String CATEGORY_ANIMAL = "animal";
    private static final String CATEGORY_MARRIAGE = "marriage";
    private static final String CATEGORY_TECH = "tech";
    private static final String CATEGORY_FAMILY = "family";

    private static final List<String> mathJokes = new ArrayList<String>() {{
        // Reference: https://unijokes.com/
        add("Little Johnny was sitting in class " +
                "doing math problems when his teacher picked him to answer a question, " +
                "\"Johnny, if there were five birds sitting on a fence and you shot one " +
                "with your gun, how many would be left?\"\n" +
                "\"None,\" replied Johnny, \"cause the rest would fly away.\"\n" +
                "\"Well, the answer is four,\" said the teacher, \"but I like the way " +
                "you\'re thinking.\" \n" + "Little Johnny says, \"I have a question for " +
                "you. If there were three women eating ice cream cones in a shop, one " +
                "was licking her cone, the second was biting her cone and the third was " +
                "sucking her cone, which one is married?\" \n" + "\"Well,\" said the " +
                "teacher nervously, \"I guess the one sucking the cone.\"\n" +
                "\"No,\" said Little Johnny, \"the one with the wedding ring on her " +
                "finger, but I like the way you\'re thinking.\"");
        // Reference: https://unijokes.com/
        add("If you had a million dollars and " +
                "gave away one quarter, and another quarter, and then another quarter," +
                " how much would you have left?\nA million dollars minus 75 cents.");
        // Reference: https://www.quickfunnyjokes.com/math.html
        add("What is a bird\'s favorite type of math?\nOwl-gebra");
        // Reference: https://thoughtcatalog.com/january-nelson/2018/04/39-math-jokes-and-puns-that-will-make-you-smile-easy-as-pi/
        add("What did the zero say to the eight?\nNice belt!");
        // Reference: https://unijokes.com/
        add("\"If you had a dollar,\" quizzed the teacher, \"and you asked your father " +
                "for another dollar and fifty cents, how much money would you have?\"\n" +
                "\"One dollar.\" answered little Johnny.\n" +
                "\"You don\'t know your basic math.\" said the teacher shaking her head, disappointed.\n" +
                "Little Johnny shook his head too, \"You don\'t know my daddy.\"");
        // Reference: https://unijokes.com/
        add("The teacher asks little Johnny if he knows his numbers.\n" +
                "\"Yes,\" he says. \"My daddy taught me.\"\n" +
                "\"Can you tell me what comes after three?\"\n" +
                "\"Four,\" answers little Johnny.\n" +
                "\"What comes after six?\"\n" +
                "\"Seven,\" answers little Johnny.\n" +
                "\"Very good,\" says the teacher. \"Your father did a very fine job.\n" +
                "What comes after ten?\"\n" +
                "\"A jack,\" answers little Johnny.");
        // Reference: https://owlcation.com/stem/Worst-Math-Jokes-and-Math-Puns
        add("My girlfriend is the square root of -100. She\'s a perfect 10, but purely " +
                "imaginary.");
        // Reference: https://unijokes.com/
        add("Son: Dad, it\'s so cold in here!\n" +
                "Father: Go stand in the corner.\n" +
                "Son: Why?\n" +
                "Father: The corner is 90 degrees.");
        // Reference: https://owlcation.com/stem/Worst-Math-Jokes-and-Math-Puns
        add("Q: Why is a math book depressed? A: Because it has so many problems.");
        // Reference: https://owlcation.com/stem/Worst-Math-Jokes-and-Math-Puns
        add("There are three people applying for the same job at a bank: a mathematician," +
                " a statistician, and an accountant. The interviewing committee asks" +
                " the mathematician one question: What is 500 plus 500? " +
                "The mathematician answers \"1000\" without hesitation, and they send " +
                "him along. Next they call in the statistician and ask the same question." +
                " He thinks for a moment and answers \"1000... I\'m 95% confident.\"" +
                " When the accountant comes in, he is asked the same question: \"What " +
                "is 500 + 500?\" He bows and replies, \"What would you like it to be?\" " +
                "They hire the accountant.");
    }};

    private static final List<String> animalJokes = new ArrayList<String>() {{
        // Reference: https://unijokes.com/
        add("One day little Johnny was digging a hole in his back yard.\n" +
                "The next-door neighbor spotted him and decided to investigate.\n" +
                "\"Hello Johnny, what are you up to?\" he asked.\n" +
                "\"My goldfish died and I\'m gonna bury him,\" Johnny replied.\n" +
                "\"That\'s a really big hole for a goldfish, isn\'t it?\" asked the neighbor.\n" +
                "\"That\'s because he\'s inside your cat!\"");
        // Reference: https://unijokes.com/
        add("If your wife is shouting at the front door and your dog is barking at the " +
                "back door, who do you let in first?\n" +
                "The dog, of course. At least he\'ll shut up after you let him in.");
        // Reference: https://unijokes.com/
        add("Why did Mozart kill all his chickens?\n" +
                "Because when he asked them who the best composer was, they\'d all say: " +
                "\"Bach, Bach, Bach.\"");
        // Reference: https://unijokes.com/
        add("Little Johnny\'s teacher said,\n" +
                "\"Johnny, your essay on My Dog is exactly the same as your sister\'s.\"\n" +
                "Did you copy hers?, she asked.\n" +
                "Johnny replied, \"No, teacher, it\'s the same dog!\"");
        // Reference: https://unijokes.com/
        add("Yo momma so fat when she went to the circus the little girl asked if she " +
                "could ride the elephant.");
        // Reference: https://unijokes.com/
        add("Yo Mama is so fat, if she buys a fur coat, a whole species will become" +
                " extinct.");
        // Reference: http://www.kidzjokes.com/milkshake-joke/
        add("Where do milkshakes come from?\nNervous cows.");
        // Reference: http://www.kidzjokes.com/10-cows-joke-funny-cows-joke/
        add("If you had ten cows and five goats what would you have?\nA whole lot of milk!");
        // Reference: http://www.kidzjokes.com/farm-joke-two-pigs-and-chicken-joke/
        add("What do you have when two pigs and a chicken get together for breakfast?\n" +
                "Sausage, ham and eggs!");
        // Reference: http://www.kidzjokes.com/dog-joke-city/
        add("What kind of dog lives in the city?\nA New Yorkie!");
        // Reference: http://www.kidzjokes.com/cow-joke-cross-cow-and-smurf/
        add("What do you get when you cross a cow with a smurf?\nBlue cheese!");
        // Reference: http://www.kidzjokes.com/beetle-and-lamb-joke-for-kids/
        add("What do you get when you cross a lamb, hummingbird and a beetle?\n" +
                "A bah-humbug!");
    }};

    private static final List<String> marriageJokes = new ArrayList<String>() {{
        // Reference: https://unijokes.com/
        add("A couple drove down a country road for several miles, not saying a word. \n" +
                "An earlier discussion had led to an argument and neither of them wanted " +
                "to concede their position. \n" +
                "As they passed a barnyard of mules, goats, and pigs, the husband asked " +
                "sarcastically,\n" + "\"Relatives of yours?\"\n" +
                "\"Yep,\" the wife replied, \"in-laws.\"");
        add("Husband to wife: \"I hear you\'ve been telling everyone that I\'m an idiot.\"\n" +
                "Wife: \"Sorry, I didn\'t know it was a secret.\"");
        add("A man went to the Police Station wishing to speak with the burglar who had " +
                "broken into his house the night before.\n" +
                "\"You\'ll get your chance in court.\" said the Desk Sergeant.\n" +
                "\"No, no no!\" said the man. \"I want to know how he got into the " +
                "house without waking my wife. \n" +
                "I\'ve been trying to do that for years!\"");
        add("A nice, calm and respectable lady went into the pharmacy, right up to the " +
                "pharmacist, looked straight into his eyes, and said, \"I would like to " +
                "buy some cyanide.\"\n" +
                "The pharmacist asked, \"Why in the world do you need cyanide?\"\n" +
                "The lady replied, \"I need it to poison my husband.\"\n" +
                "The pharmacists eyes got big and he exclaimed, \"Lord have mercy! " +
                "I can\'t give you cyanide to kill your husband! That\'s against the law! " +
                "I\'ll lose my license! They\'ll throw both of us in jail! All kinds of " +
                "bad things will happen. Absolutely not! You CANNOT have any cyanide!\"\n" +
                "The lady reached into her purse and pulled out a picture of her husband " +
                "in bed with the pharmacist\'s wife. \n" +
                "The pharmacist looked at the picture and replied, \"Well now. " +
                "That\'s different. You didn\'t tell me you had a prescription.\"");
        add("A wife got so mad at her husband she packed his bags and told him to get out. \n" +
                "As he walked to the door she yelled, \"I hope you die a long, slow, " +
                "painful death.\" \n" + "He turned around and said, \"So, you want me " +
                "to stay?\"");
        add("The man approached the very beautiful woman in the large supermarket " +
                "and asked, \n" +
                "\"You know, I\'ve lost my wife here in the supermarket.\n" +
                "Can you talk to me for a couple of minutes?\"\n" +
                "\"Why?\"\n" +
                "\"Because every time I talk to a beautiful woman my wife appears out " +
                "of nowhere.\"");
        add("In the first year of marriage, the man speaks and the woman listens. \n" +
                "In the second year, the woman speaks and the man listens. \n" +
                "In the third year, they both speak and the neighbors listen.");
        add("Wife: Why do you go out in the balcony, when I start singing.\n" +
                "Husband: Because the people would think I am beating you.");
        add("A woman was telling her friend, \"It was I who made my husband a " +
                "millionaire.\"\n" +
                "\"And what was he before you married him?\" asked the friend. \n" +
                "The woman replied, \"A multi-millionaire\".");
    }};

    private static final List<String> techJokes = new ArrayList<String>() {{
        // Reference: https://unijokes.com/
        add("Girls are like Internet Domain names, the ones I like are already taken.");
        add("Where\'s the best place to hide a body?\nPage two of Google.");
        add("There was once a young man who, in his youth, professed his desire to " +
                "become a great writer.\n" +
                "When asked to define \"Great\" he said,\n" +
                "\"I want to write stuff that the whole world will read, stuff that " +
                "people will react to on a truly emotional level, stuff that will make " +
                "them scream, cry, howl in pain and anger!\"\n" +
                "He now works for Microsoft, writing error messages.");
        add("I changed my password to \"incorrect\". So whenever I forget what it is " +
                "the computer will say \"Your password is incorrect\".");
        add("A man flying in a hot air balloon suddenly realizes he\'s lost. \n" +
                "He reduces height and spots a man down below. \n" +
                "He lowers the balloon further and shouts to get directions, \"Excuse " +
                "me, can you tell me where I am?\"\n" +
                "The man below says: \"Yes. You\'re in a hot air balloon, hovering 30 " +
                "feet above this field.\"\n" +
                "\"You must work in Information Technology,\" says the balloonist.\n" +
                "\"I do\" replies the man. \"How did you know?\"\n" +
                "\"Well,\" says the balloonist, \"everything you have told me is " +
                "technically correct, but It\'s of no use to anyone.\"\n" +
                "The man below replies, \"You must work in management.\"\n" +
                "\"I do,\" replies the balloonist, \"But how\'d you know?\"\n" +
                "\"Well\", says the man, \"you don\'t know where you are or where " +
                "you\'re going, but you expect me to be able to help. You\'re in the " +
                "same position you were before we met, but now it\'s my fault.\"");
        add("Reaching the end of a job interview, the Human Resources Officer asked " +
                "a young Engineer fresh out of MIT, \"And what starting salary were " +
                "you looking for?\"\n" +
                "The Engineer replies, \"In the region of $125,000 a year, depending " +
                "on the benefits package.\"\n" +
                "The interviewer enquires, \"Well, what would you say to a package " +
                "of 5-weeks vacation, 14 paid holidays, full medical and dental, " +
                "company matching retirement fund to 50% of salary, and a company car " +
                "leased every 2 years say, a red Corvette?\"\n" +
                "The Engineer sits up straight and says, \"Wow! Are you kidding?\"\n" +
                "And the interviewer replies, \"Yeah, but you started it.\"");
        add("Wikipedia: I know everything! \n" +
                "Google: I have everything! \n" +
                "Facebook: I know everybody! \n" +
                "Internet: Without me you are nothing!\n" +
                "Electricity: Keep talking b******!");
        add("If at first you don\'t succeed, call it version 1.0.");
        add("My wifi suddenly stop working then I realized that my neighbors have " +
                "not paid the bill. \n" +
                "How irresponsible people are.");
    }};

    private static final List<String> familyJokes = new ArrayList<String>() {{
        // Reference: https://short-funny.com/new-jokes.php
        add("Father: \"Son, you were adopted.\"\n" +
                " \n" + "Son: \"What?! I knew it! I want to meet my biological parents!\"\n" +
                " \n" + "Father: \"We are your biological parents. Now pack up, " +
                "the new ones will pick you up in 20 minutes.\"\n" +
                "\n");
        // Reference: https://bskud.com/jokes/english/latest.php
        add("Son: \"Dad, my stomach is paining.\"\n" + "Father: \"That\'s because your " +
                "stomach is empty.\"\n" +
                "Son: \"Oh! Now I understand why you always have headache.\"");
        // Reference: https://unijokes.com/
        add("Three highly decorated police officers die in a wild shoot out with " +
                "narcotics dealers and go to heaven.\n" +
                "God greets them and asks, \"When you are laid out in your casket, " +
                "and your fellow officers and family are mourning you, what would you " +
                "like to hear them say about you?\n" +
                "The first cop says, \"I would like to hear them say, that I was the " +
                "bravest cop on the force.\"\n" +
                "The second police officer says, \"I would like to hear that I was a " +
                "terrific cop who died in the line of duty.\"\n" +
                "The last cop replies, \"I would like to hear them say ... Look, " +
                "He\'s Moving!\"");
        // Reference: https://unijokes.com/
        add("One day, during a lesson on proper grammar, the teacher asked for a " +
                "show of hands for who could use the word \"beautiful\" in the same " +
                "sentence twice.\n" +
                "First, she called on little Suzie, who responded with, \"My father " +
                "bought my mother a beautiful dress and she looked beautiful in it.\"\n" +
                "\"Very good, Suzie,\" replied the teacher. \n" +
                "She then called on little Michael.\n" +
                "\"My mommy planned a beautiful banquet and it turned out beautifully, " +
                "he said.\n" +
                "\"Excellent, Michael!\"\n" +
                "Then, the teacher called on little Johnny.\n" +
                "\"Last night, at the dinner table, my sister told my father that she " +
                "was pregnant, and he said, \'Beautiful, f***ing beautiful!'\"");
        // Reference: https://unijokes.com/
        add("Thomas is 32 years old and he is still single.\n" +
                "One day a friend asked, \"Why aren\'t you married? Can\'t you find a " +
                "woman who will be a good wife?\"\n" +
                "Thomas replied, \"Actually, I\'ve found many women I wanted to marry, " +
                "but when I bring them home to meet my parents, my mother doesn\'t like them.\"\n" +
                "His friend thinks for a moment and says, \"I've got the perfect " +
                "solution, just find a girl who\'s just like your mother.\"\n" +
                "A few months later they meet again and his friend says, \"Did you find " +
                "the perfect girl? Did your mother like her?\"\n" +
                "With a frown on his face, Thomas answers, \"Yes, I found the perfect " +
                "girl. She was just like my mother. You were right, my mother liked her " +
                "very much.\"\n" + "The friend said, \"Then what\'s the problem?\"\n" +
                "Thomas replied, \"My father doesn\'t like her.\"");
        // Reference: https://unijokes.com/
        add("Son: \"Daddy; why some of your hairs have turned white?\"\n" +
                "Father: \"Every lie told by you makes one of my hairs white.\"\n" +
                "Son: \"Oh now I understood why all grandfathers\' hairs are white.\"");

    }};

    /**
     * Returns list of jokes from each category.
     *
     * @param category The joke category
     */
    public static List<String> getJokes(String category) {
        List<String> jokes;
        switch (category) {
            case CATEGORY_MATH:
                jokes = mathJokes;
                break;
            case CATEGORY_ANIMAL:
                jokes = animalJokes;
                break;
            case CATEGORY_MARRIAGE:
                jokes = marriageJokes;
                break;
            case CATEGORY_TECH:
                jokes = techJokes;
                break;
            case CATEGORY_FAMILY:
                jokes = familyJokes;
                break;
            default:
                jokes = new ArrayList<>();
                break;
        }
        return jokes;
    }
}

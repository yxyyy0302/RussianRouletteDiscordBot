package russianroulettebot;

import commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault(Token.getTOKEN()).enableIntents(GatewayIntent.DIRECT_MESSAGES,GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS).build();

        CommandManager manager = new CommandManager();
        manager.add(new Hello());
        manager.add(new Curse());
        manager.add(new Hbd());
        manager.add(new WhatsThis());
        manager.add(new Kill());
        manager.add(new Bully());
        manager.add(new Help());
        manager.add(new Rules());

        jda.addEventListener(new Listeners());
        jda.addEventListener(new RussianRouletteGame());
        jda.addEventListener(manager);
    }
}
package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import russianroulettebot.Command;

import java.awt.*;
import java.util.List;

public class Rules implements Command {

    @Override
    public String getName() {
        return "rules";
    }

    @Override
    public String getDescription() {
        return "Return the rules of our Russian Roulette game";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor("This is a unique type of Russian Roulette and I hope you find it fun!");
        embedBuilder.setTitle("**Russian Roulette Rules**");
        Color color = new Color(3, 211, 252);
        embedBuilder.setColor(color);

        embedBuilder.addField("Rule 1","Type \"rr\" to start the game. There is a gun with 6 chambers. Once you start the game, a random chamber will be loaded with a bullet.",false);
        embedBuilder.addField("Rule 2", "You will choose between fire or spin. Fire will attempt to shoot the pistol, while spinning will randomize the chamber that the bullet is in place before attempting to shoot the pistol.",false);
        embedBuilder.addField("Rule 3", "You will be playing with a bot (me :3) and we will take turns shooting the pistol until one of us dies. The winner will be shown at the end of the game.",false);
        embedBuilder.addField("Rule 4", "If the gun does not go off, the gun will be passed to the other player and the game will continue until either players die.", false);
        embedBuilder.addField("Rule 5", "Unfortunately, only one player can play the game at the same time, so you should invite this bot to a private server or channel. In that way, nobody can interefere with your game. I'm trying to update the game so that it can support more than one player playing at the same time in the future!",false);

        embedBuilder.setFooter("Enjoy playing the game 😊");
        MessageEmbed embed = embedBuilder.build();
        event.replyEmbeds(embed).queue();
    }
}

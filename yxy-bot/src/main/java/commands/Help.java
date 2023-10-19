package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import russianroulettebot.CommandManager;
import russianroulettebot.Command;

import java.awt.*;
import java.util.List;

public class Help implements Command {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Returns the list of commands";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        StringBuilder commandList = new StringBuilder();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor("Hi there, I've made a list of commands for you");
        embedBuilder.setTitle("List of Commands");
        embedBuilder.setColor(Color.pink);

        for (Command command : CommandManager.getCommands()) {
            if (!command.getName().equals("help")) {
                embedBuilder.addField("/**" + command.getName() + "**", command.getDescription(), false);
            }
        }
        embedBuilder.setFooter("We have more features! Type \"rr\" to play our Russian Roulette game.");
        MessageEmbed embed = embedBuilder.build();
        event.replyEmbeds(embed).queue();
    }
}

package russianroulettebot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.EventListener;
import java.util.Random;

public class Listeners extends ListenerAdapter implements EventListener {
    private RussianRouletteGame game;

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.println("Online");
        int numberOfGuilds = event.getJDA().getGuilds().size();
        String activityText = "Over " + numberOfGuilds + " Servers.";
        event.getJDA().getPresence().setActivity(Activity.playing("Russian Roulette"));
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (message.equalsIgnoreCase("rr")) {
            game = new RussianRouletteGame();
            game.setGameStarted(event.getAuthor());
            event.getChannel().sendMessage("**" + event.getAuthor().getName() + "**, You have chosen to play \uD83D\uDD2B **Russian Roulette.** There " +
                    "are **6** chambers and the bullet will be randomly inserted.\nYou can use **/rules** to access the rules of this game.").queue();
            if (!game.isGameStarted()) {
                game.setGameStarted(event.getMessage().getAuthor());
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle(event.getAuthor().getName());
            embed.setDescription("Do you want to fire or spin the gun?");

            net.dv8tion.jda.api.interactions.components.buttons.Button fire = net.dv8tion.jda.api.interactions.components.buttons.Button.danger("fire-button", "Fire");
            net.dv8tion.jda.api.interactions.components.buttons.Button spin = net.dv8tion.jda.api.interactions.components.buttons.Button.success("spin-button", "Spin");

            MessageAction messageAction = event.getChannel().sendMessageEmbeds(embed.build())
                    .setActionRows(ActionRow.of(fire, spin));

            messageAction.queue();
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (!event.getUser().equals(game.getGameStarter())) {
            event.reply(event.getUser().getAsMention() + ", it's not your turn."
                    + "Wait for **" + game.getGameStarter().getName() + "** to finish.").queue();
            return;
        }
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Game Result");
        embedBuilder.setColor(Color.red);
        if (event.getButton().getId().equals("fire-button")) {
            if (game.isGameStarted()) {
                if (game.isGameLost()) {
                    game.endGame();
                    embedBuilder.setDescription(event.getUser().getAsMention() + ", you chose to **fire**.\n" +
                            "You fired the bullet! You lost the game. ☠");
                    event.replyEmbeds(embedBuilder.build()).queue();
                    return;
                } else {
                    event.getChannel().sendMessage(event.getUser().getAsMention() + ", you chose to **fire. **\n" +
                            "You have survived! The chamber was not " +
                            "loaded. The gun will be passed to the bot now.").queue();
                    game.rotateChamberOne();
                    Random rd = new Random();
                    int botChoice = rd.nextInt(2);
                    if (botChoice == 1) {
                        game.rotateChamber();
                        if (game.isGameLost()) {
                            game.endGame();
                            embedBuilder.setDescription("The bot has chosen to **spin the gun**.\nRandomly Spinning......\n" +
                                    "The bot fired after spinning the gun.\nThe bot has fired the bullet!\n" +
                                    "The winner is: " + event.getUser().getAsMention() + "! Congratulations! 🎉");
                            event.replyEmbeds(embedBuilder.build()).queue();
                            return;
                        } else {
                            event.getChannel().sendMessage("The bot has chosen to **fire**." +
                                    "\nThe bot did not fire the bullet. Now it's your turn.").queue();
                            game.rotateChamberOne();
                            sendFireOrSpinMessage(event.getChannel(), game.getGameStarter());
                        }
                    } else {
                        if (game.isGameLost()) {
                            game.endGame();
                            embedBuilder.setDescription("The bot has chosen to **fire**.\nThe bot has fired the bullet!\nThe winner is: " + event.getUser().getAsMention()
                                    + "! Congratulations! 🎉");
                            event.replyEmbeds(embedBuilder.build()).queue();
                            return;
                        } else {
                            event.getChannel().sendMessage("The bot has chosen to **fire**. " +
                                    "\nThe bot did not fire the bullet. Now it's your turn.").queue();
                            game.rotateChamberOne();
                            sendFireOrSpinMessage(event.getChannel(), game.getGameStarter());
                        }
                    }

                }
            }
            event.getMessage().delete().queue();
        } else if (event.getButton().getId().equals("spin-button")) {
            if (game.isGameStarted()) {
                game.rotateChamber();
                if (game.isGameLost()) {
                    game.endGame();
                    embedBuilder.setDescription(event.getUser().getAsMention() + ", you chose to **spin** the gun.\nRandomly Spinning......" +
                            "\nYou fired the gun after spinning." + "\n" + event.getUser().getAsMention() + ". you fired the bullet! You lost the game. ☠");
                    event.replyEmbeds(embedBuilder.build()).queue();
                    return;
                } else {
                    event.getChannel().sendMessage(event.getUser().getAsMention() + ", you chose to **spin** the gun.\nRandomly Spinning......" +
                            "\nYou fired the gun after spinning.\n" + event.getUser().getAsMention() + ", you survived! The chamber was not " +
                            "loaded. The gun will be passed to the bot now.").queue();
                    game.rotateChamberOne();
                    Random rd = new Random();
                    int botChoice = rd.nextInt(2);
                    if (botChoice == 1) {
                        game.rotateChamber();
                        if (game.isGameLost()) {
                            game.endGame();
                            embedBuilder.setDescription("The bot has chosen to **spin the gun**.\nRandomly Spinning......\n" +
                                    "The bot fired after spinning the gun.\nThe bot has fired the bullet!\n" +
                                    "The winner is: " + event.getUser().getAsMention() + "! Congratulations! 🎉");
                            event.replyEmbeds(embedBuilder.build()).queue();
                            return;
                        } else {
                            event.getChannel().sendMessage("The bot has chosen to **fire**." +
                                    "\nThe bot did not fire the bullet. Now it's your turn.").queue();
                            game.rotateChamberOne();
                            sendFireOrSpinMessage(event.getChannel(), game.getGameStarter());
                        }
                    } else {
                        if (game.isGameLost()) {
                            game.endGame();
                            embedBuilder.setDescription("The bot has chosen to **fire**. \nThe bot has fired the bullet!\n" +
                                    "The winner is: "
                                    + event.getUser().getAsMention() + "! Congratulations! 🎉");
                            event.replyEmbeds(embedBuilder.build()).queue();
                            return;
                        } else {
                            event.getChannel().sendMessage("The bot has chosen to **fire**." +
                                    "\nThe bot did not fire the bullet. Now it's your turn.").queue();
                            game.rotateChamberOne();
                            sendFireOrSpinMessage(event.getChannel(), game.getGameStarter());
                        }

                    }
                }
            }
            event.getMessage().delete().queue();

        }

    }

    private void sendFireOrSpinMessage(MessageChannel channel, User user) {
        net.dv8tion.jda.api.interactions.components.buttons.Button fire = net.dv8tion.jda.api.interactions.components.buttons.Button.danger("fire-button", "Fire");
        net.dv8tion.jda.api.interactions.components.buttons.Button spin = Button.success("spin-button", "Spin");
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color randomColor = new Color(r, g, b);
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(randomColor);
        embed.setTitle(user.getName());
        embed.setDescription("Do you want to fire or spin the gun again?");
        MessageAction messageAction = channel.sendMessageEmbeds(embed.build())
                .setActionRows(ActionRow.of(fire, spin));

        messageAction.queue();
    }
}

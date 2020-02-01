package fr.antoinerochas.cerebrum.order;

import fr.antoinerochas.cerebrum.Cerebrum;
import fr.antoinerochas.cerebrum.i18n.I18NManager;
import fr.antoinerochas.cerebrum.i18n.Language;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This file is part of Cerebrum.
 * Is responsible for taking orders from customers.
 *
 * @author Aro at/on 28/01/2020
 * @since 1.0
 */
public class OrderManager
{
    /**
     * Log4J's {@link Logger} instance.
     */
    public static final Logger LOGGER = LogManager.getLogger(OrderManager.class);

    /**
     * The {@link JDA} instance.
     */
    private JDA jda;

    /**
     * The current {@link OrderManager}'s status.
     */
    private OrderStatus status = OrderStatus.OFF;

    /**
     * Constructor.
     *
     * @param jda the current {@link JDA} instance
     */
    public OrderManager(JDA jda)
    {
        LOGGER.debug("Loading OrderManger...");
        // Define JDA instance.
        this.jda = jda;
        // If we're under Debug Mode, set the status in consequence.
        if (Cerebrum.DEBUG) { this.setStatus(OrderStatus.DEBUG); return; }
        //Else, set OrderManager under the default status.
        this.setStatus(OrderStatus.AVAILABLE);
    }

    /**
     * Start the order process from a {@link User}.
     *
     * @param user the user that took an {@link Order}
     */
    public void startOrderProcess(User user)
    {
        // If orders are disabled, tell the user!
        if (status != OrderStatus.AVAILABLE)
        {
            Language language = I18NManager.getUserLanguage(user);
            PrivateChannel channel = user.openPrivateChannel().complete();
            //channel.sendMessage()
            return;
        }

        LOGGER.info("Taking order from " + user.getName() + "(" + user.getId() + ")...");
    }

    /**
     * Set the current {@link OrderManager}'s status.
     *
     * @param status the new status {@link OrderManager} will be in
     */
    public void setStatus(OrderStatus status)
    {
        LOGGER.debug("Modifying OMS " + this.status.name() + " -> " + status.name());
        this.status = status;
        // Print the status change.
        LOGGER.info("OrderManager Status has been switched to " + status.name() + ". Applying changes...");
    }
}

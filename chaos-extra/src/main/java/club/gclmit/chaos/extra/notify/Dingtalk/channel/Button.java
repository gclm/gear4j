package club.gclmit.chaos.extra.notify.Dingtalk.channel;

/**
 * ActionCard Button 按钮
 *
 * @author gclm
 */
public class Button {

    private String title;
    private String actionURL;

    public Button(String title, String actionURL) {
        this.title = title;
        this.actionURL = actionURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActionURL() {
        return actionURL;
    }

    public void setActionURL(String actionURL) {
        this.actionURL = actionURL;
    }
}

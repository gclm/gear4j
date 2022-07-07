package club.gclmit.gear4j.extra.notify.dingtalk.channel;

/**
 * ActionCard Button 按钮
 *
 * @author gclm
 */
public class Button {

    private String title;
    private String actionUrl;

    public Button(String title, String actionUrl) {
        this.title = title;
        this.actionUrl = actionUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }
}

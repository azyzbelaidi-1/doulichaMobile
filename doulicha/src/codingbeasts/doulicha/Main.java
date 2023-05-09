package codingbeasts.doulicha;

import codingbeasts.doulicha.view.DiscussionsForm;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;


public class Main {
    private Form current;
    private Resources theme;

    public void init(Object context)   {
        theme = UIManager.initFirstTheme("/theme");
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        DiscussionsForm homeForm = new DiscussionsForm();
        homeForm.show();
    }

    public void stop() {
        current = com.codename1.ui.Display.getInstance().getCurrent();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = com.codename1.ui.Display.getInstance().getCurrent();
        }
    }
    
    public void destroy() {
    }
}

package view;

import javafx.scene.control.ComboBox;

public class MateriaFixLesson implements SendableFilling {
    public ComboBox pabellonButton;

    private HomeWindowCntlr home;

    @Override
    public void setFillingReceiver(HomeWindowCntlr homeCntlr) {
        this.home = homeCntlr;
    }
}

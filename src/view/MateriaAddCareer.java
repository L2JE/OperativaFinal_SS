package view;

public class MateriaAddCareer implements SendableFilling {
    private HomeWindowCntlr homeCntlr;

    @Override
    public void setFillingReceiver(HomeWindowCntlr homeCntlr) {
        this.homeCntlr = homeCntlr;
    }
}

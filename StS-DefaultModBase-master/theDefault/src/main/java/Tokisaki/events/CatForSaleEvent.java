package Tokisaki.events;

import Tokisaki.DefaultMod;
import Tokisaki.cards.*;
import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static Tokisaki.DefaultMod.makeEventPath;
import static Tokisaki.characters.TheDefault.Enums.TOKISAKI;

public class CatForSaleEvent extends AbstractImageEvent {


    public static final String ID = DefaultMod.makeID("CatForSaleEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("CatForSaleEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    public CatForSaleEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[0]); // Inspiration - Gain a Random Starting Relic
        imageEventText.setDialogOption(OPTIONS[1]); // Denial - lose healthDamage Max HP
        imageEventText.setDialogOption(OPTIONS[2]); // Acceptance - Gain Apotheosis
    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Inspiration.
                        int num = AbstractDungeon.player.gold / 30;
                        AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);

                        if(num == 0) num = 1;

                        for(int j=0;j<num;j++)
                        {
                            AbstractCard c;
                            int rnd = (int)(Math.random() * 16);

                            switch(rnd)
                            {
                                case 0: //데미지
                                    c = new Cat().makeCopy();
                                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                                    break;
                                case 1: //치유
                                    c = new CatKingdom().makeCopy();
                                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                                    break;
                                case 2:
                                    c = new ActinidiaBranch().makeCopy();
                                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                                    break;
                                case 3:
                                    c = new Chur().makeCopy();
                                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                                    break;
                                case 4:
                                    c = new CatTower().makeCopy();
                                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                                    break;
                                case 5:
                                    c = new PawPad().makeCopy();
                                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                                    break;
                                case 6:
                                    c = new ShadowCat().makeCopy();
                                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                                    break;
                                case 7:
                                    c = new CatPunch().makeCopy();
                                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                                    break;
                                case 8:
                                    c = new ALittleBigCat().makeCopy();
                                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                                    break;
                                case 9:
                                    c = new BrokenCat().makeCopy();
                                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                                    break;
                                case 10:
                                    c = new CatHand().makeCopy();
                                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                                    break;
                                case 11:
                                    c = new DayOfCat().makeCopy();
                                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                                    break;
                                case 12:
                                    c = new CorrosionCat().makeCopy();
                                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                                    break;
                                case 13:
                                    c = new CatIsBestMedicine().makeCopy();
                                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                                    break;
                                case 14:
                                    c = new Nekomimi().makeCopy();
                                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                                    break;
                                case 15:
                                    c = new WholeWorldIsCat().makeCopy();
                                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                                    break;
                            }
                        }

                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 1: // If you press button the second button (Button at index 1), in this case: Denial
                        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0) {
                            // If you have cards you can remove - remove a card
                            AbstractDungeon.gridSelectScreen.open(
                                    CardGroup.getGroupWithoutBottledCards(
                                            AbstractDungeon.player.masterDeck.getPurgeableCards()),
                                    1, OPTIONS[4], false, false, false, true);
                        }
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 2: // If you press button the third button (Button at index 2), in this case: Acceptance
                        AbstractDungeon.player.increaseMaxHp(10,true);

                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                }
                break;
            case 1: // Welcome to screenNum = 1;
                switch (i) {
                    case 0: // If you press the first (and this should be the only) button,
                        openMap(); // You'll open the map and end the event.
                        break;
                }
                break;
        }
    }

    public void update() { // We need the update() when we use grid screens (such as, in this case, the screen for selecting a card to remove)
        super.update(); // Do everything the original update()
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) { // Once the grid screen isn't empty (we selected a card for removal)
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0); // Get the card
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2))); // Create the card removal effect
            AbstractDungeon.player.masterDeck.removeCard(c); // Remove it from the deck
            AbstractDungeon.gridSelectScreen.selectedCards.clear(); // Or you can .remove(c) instead of clear,
            // if you want to continue using the other selected cards for something
        }

    }

}

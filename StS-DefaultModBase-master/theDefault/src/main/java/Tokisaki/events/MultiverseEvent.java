package Tokisaki.events;

import Tokisaki.DefaultMod;
import Tokisaki.cards.BulletReplenishment;
import Tokisaki.cards.Mozambique;
import Tokisaki.cards.PawPad;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static Tokisaki.DefaultMod.makeEventPath;

public class MultiverseEvent extends AbstractImageEvent {


    public static final String ID = DefaultMod.makeID("MultiverseEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("MultiverseEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    public MultiverseEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[0], new Mozambique()); // Inspiration - Gain a Random Starting Relic
        imageEventText.setDialogOption(OPTIONS[1], new BulletReplenishment()); // Denial - lose healthDamage Max HP
        imageEventText.setDialogOption(OPTIONS[2], new PawPad()); // Acceptance - Gain Apotheosis
    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Inspiration.
                        imageEventText.loadImage("TokisakiResources/images/events/MultiverseEvent2.png");
                        AbstractCard c = new Mozambique().makeCopy();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 1: // If you press button the second button (Button at index 1), in this case: Denial
                        imageEventText.loadImage("TokisakiResources/images/events/MultiverseEvent3.png");
                        AbstractCard ca = new BulletReplenishment().makeCopy();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(ca, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 2: // If you press button the third button (Button at index 2), in this case: Acceptance
                        imageEventText.loadImage("TokisakiResources/images/events/MultiverseEvent4.png");
                        AbstractCard car = new PawPad().makeCopy();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(car, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

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

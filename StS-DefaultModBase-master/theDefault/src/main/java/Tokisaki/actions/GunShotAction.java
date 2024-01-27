package Tokisaki.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

public class GunShotAction extends AbstractGameAction {

    public GunShotAction() {
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {
        CardCrawlGame.sound.playA("ORB_LIGHTNING_PASSIVE", 1.0f);
        this.isDone = true;
    }
}

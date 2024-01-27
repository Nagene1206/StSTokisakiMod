package Tokisaki.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class PsychoAction extends AbstractGameAction {
    UUID uuid;

    public PsychoAction(UUID targetUUID) {
        setValues(this.target, this.source);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.uuid = targetUUID;
    }

    public void update() {
        for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            int tmp = c.baseDamage;
            c.baseDamage = c.baseBlock;
            c.baseBlock = tmp;
        }
        this.isDone = true;
    }
}

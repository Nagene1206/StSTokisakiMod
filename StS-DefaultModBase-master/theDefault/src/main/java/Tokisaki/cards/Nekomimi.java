package Tokisaki.cards;

import Tokisaki.DefaultMod;
import Tokisaki.characters.TheDefault;
import Tokisaki.powers.NekomimiPower;
import Tokisaki.powers.ShadowRainPower;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Tokisaki.DefaultMod.makeCardPath;

public class Nekomimi extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * In-Progress Form At the start of your turn, play a TOUCH.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Nekomimi.class.getSimpleName());
    public static final String IMG = makeCardPath("Nekomimi.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int MAGIC_NUMBER = 1;
    private static final int UPGRADE_MAGIC_NUMBER = -1;
    // /STAT DECLARATION/


    public Nekomimi() {

        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        magicNumber = baseMagicNumber = MAGIC_NUMBER;

        this.tags.add(BaseModCardTags.FORM); //Tag your strike, defend and form cards so that they work correctly.
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(AbstractDungeon.player.hasPower(NekomimiPower.POWER_ID))
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, NekomimiPower.POWER_ID));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NekomimiPower(p,magicNumber)));
        for(AbstractCard c:AbstractDungeon.player.hand.group)
        {
            c.setCostForTurn(magicNumber);
        }
        AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(2-magicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}

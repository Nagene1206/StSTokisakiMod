package Tokisaki.cards;

import Tokisaki.DefaultMod;
import Tokisaki.characters.TheDefault;
import Tokisaki.powers.CatPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.Random;

import static Tokisaki.DefaultMod.makeCardPath;

public class CatHand extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(CatHand.class.getSimpleName());
    public static final String IMG = makeCardPath("CatHand.png");

    // /TEXT DECLARATION/
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 3;
    private static final int DAMAGE = 1;
    private static final int BLOCK = 1;
    private static final int MAGIC_NUMBER = 1;
    private static final int UPGRADE_COST = 2;

    // /STAT DECLARATION/


    public CatHand() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        damage = baseDamage = DAMAGE;
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = MAGIC_NUMBER;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doing(p,m);
        if(p.hasPower(CatPower.POWER_ID))
        {
            for(int i=0;i<p.getPower(CatPower.POWER_ID).amount;i++)
            {
                doing(p,m);
            }
        }
    }

    private void doing(AbstractPlayer p, AbstractMonster m) {
        int rnd = (int)(Math.random() * 13);

        switch(rnd)
        {
            case 0: //데미지
                AbstractDungeon.actionManager.addToBottom(new DamageAction(p,new DamageInfo(p,damage,damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                break;
            case 1: //치유
                AbstractDungeon.actionManager.addToBottom(new HealAction(p,p,magicNumber));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(magicNumber));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Wound(),magicNumber));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new StrengthPower(p,magicNumber)));
                break;
            case 5:
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, block));
                break;
            case 6:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new BufferPower(p,magicNumber)));
                break;
            case 7:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new IntangiblePower(p, magicNumber)));
                break;
            case 8:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new EntanglePower(p)));
            case 9:
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(magicNumber));
            case 10:
                AbstractCard c = AbstractDungeon.returnTrulyRandomColorlessCardInCombat(AbstractDungeon.cardRandomRng).makeCopy();
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, 1));
                break;
            case 11:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new BarricadePower(p)));
                break;
            case 12:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new FeelNoPainPower(p,magicNumber)));
                break;
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}

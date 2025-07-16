package tisHermitBooster.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.relics.CustomMultiplayerRelic;
import spireTogether.screens.trading.PreTradingScreen;
import spireTogether.util.SpireHelp;
import tisHermitBooster.util.TextureLoader;

import java.util.ArrayList;

import static tisHermitBooster.tisHermitBoosterMod.makeID;
import static tisHermitBooster.tisHermitBoosterMod.relicPath;

public class CursedChest extends CustomMultiplayerRelic {
    public static final String ID = makeID("CursedChest");
    private static final Texture IMG = TextureLoader.getTexture(relicPath("CursedChest.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(relicPath("CursedChestOutline.png"));
    private int amount = 60;

    public CursedChest() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.SOLID);
    }

    public ArrayList<AbstractCard> onTradeToModifyReceivingCards(P2PPlayer tradedPlayer, PreTradingScreen.TradeType tradingType, ArrayList<AbstractCard> receivingCards) {
        int goldGain = 0;
        for(AbstractCard c : receivingCards) {
            if(c.type == AbstractCard.CardType.CURSE){
                goldGain += amount;
            }
        }
        if(goldGain > 0) {
            this.flash();
            AbstractDungeon.player.gainGold(goldGain);
        }
        return receivingCards;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

}

package tisHermitBooster.relics;

import basemod.helpers.RelicType;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.relics.CustomMultiplayerRelic;
import tisHermitBooster.util.TextureLoader;

import static tisHermitBooster.tisHermitBoosterMod.makeID;
import static tisHermitBooster.tisHermitBoosterMod.relicPath;

public class WantedPoster extends CustomMultiplayerRelic {
    public static final String ID = makeID("WantedPoster");
    private static final Texture IMG = TextureLoader.getTexture(relicPath("example.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(relicPath("example.png"));

    public WantedPoster() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.SHOP, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void triggerRelic(P2PPlayer np){
        this.flash();
        np.addPower(new StrengthPower(AbstractDungeon.player, 1));
        np.addPower(new DexterityPower(AbstractDungeon.player, 1));
        np.addPower(new FocusPower(AbstractDungeon.player, 1));
    }

}

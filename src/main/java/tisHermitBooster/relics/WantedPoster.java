package tisHermitBooster.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import spireTogether.monsters.CharacterEntity;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.relics.CustomMultiplayerRelic;
import tisHermitBooster.util.TextureLoader;

import static tisHermitBooster.tisHermitBoosterMod.makeID;
import static tisHermitBooster.tisHermitBoosterMod.relicPath;

public class WantedPoster extends CustomMultiplayerRelic {
    public static final String ID = makeID("WantedPoster");
    private static final Texture IMG = TextureLoader.getTexture(relicPath("WantedPoster.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(relicPath("WantedPosterOutline.png"));

    public WantedPoster() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.FLAT);
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

    public void triggerRelic(CharacterEntity pc){
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(pc, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(pc, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(pc, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, 1)));
    }

}

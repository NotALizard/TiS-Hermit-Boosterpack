package tisHermitBooster.relics;

import com.badlogic.gdx.graphics.Texture;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.relics.CustomMultiplayerRelic;
import spireTogether.util.SpireHelp;
import tisHermitBooster.util.TextureLoader;

import static tisHermitBooster.tisHermitBoosterMod.makeID;
import static tisHermitBooster.tisHermitBoosterMod.relicPath;

public class ElevenGallonHat extends CustomMultiplayerRelic {
    public static final String ID = makeID("ElevenGallonHat");
    private static final Texture IMG = TextureLoader.getTexture(relicPath("ElevenGallonHat.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(relicPath("ElevenGallonHatOutline.png"));
    private boolean usedThisTurn = false;

    public ElevenGallonHat() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void triggerRelic(){
        if(!usedThisTurn){
            P2PPlayer rp = SpireHelp.Multiplayer.Players.GetRandomPlayer(true, true);
            if(rp != null){
                this.flash();
                this.pulse = false;
                rp.gainEnergy(1);
                usedThisTurn = true;
            }
        }
    }

    public void atTurnStart() {
        this.beginPulse();
        this.pulse = true;
        this.usedThisTurn = false;
    }

}

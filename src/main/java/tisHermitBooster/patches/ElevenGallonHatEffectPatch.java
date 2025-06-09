package tisHermitBooster.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hermit.cards.AbstractHermitCard;
import tisHermitBooster.relics.ElevenGallonHat;

@SpirePatch2(
        clz = AbstractHermitCard.class,
        method = "TriggerDeadOnEffect",
        requiredModId = "hermit",
        optional = true
)
public class ElevenGallonHatEffectPatch {
    public static void Postfix(AbstractHermitCard __instance, AbstractPlayer p, AbstractMonster m) {
        for(AbstractRelic c : AbstractDungeon.player.relics) {
            if (c.relicId.equals(ElevenGallonHat.ID)) {
                ElevenGallonHat hat = (ElevenGallonHat)c;
                hat.triggerRelic();
            }
        }
    }
}
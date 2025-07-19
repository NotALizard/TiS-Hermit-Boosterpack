package tisHermitBooster.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spireTogether.monsters.CharacterEntity;
import spireTogether.network.P2P.P2PPlayer;
import tisHermitBooster.relics.WantedPoster;

public class WantedPosterEffectPatches {
    @SpirePatch2(
            clz = P2PPlayer.class,
            method = "addPower",
            requiredModId = "spireTogether",
            optional = true
    )
    public static class WantedPosterP2PAddPowerPatch {
        public static void Postfix(P2PPlayer __instance, AbstractPower powerToApply) {
            AbstractPlayer p = AbstractDungeon.player;
            if (powerToApply.type == AbstractPower.PowerType.DEBUFF && p.hasRelic(WantedPoster.ID)) {
                WantedPoster wp = (WantedPoster) p.getRelic(WantedPoster.ID);
                wp.triggerRelic(__instance);
            }
        }
    }
    @SpirePatch2(
            clz = ApplyPowerAction.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {
                AbstractCreature.class,
                AbstractCreature.class,
                AbstractPower.class,
                int.class,
                boolean.class,
                AbstractGameAction.AttackEffect.class
            },
            requiredModId = "spireTogether",
            optional = true
    )
    public static class WantedPosterApplyPowerPatch {
        public static void Prefix(ApplyPowerAction __instance, AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast, AbstractGameAction.AttackEffect effect) {
            AbstractPlayer p = AbstractDungeon.player;
            if (target instanceof CharacterEntity && powerToApply.type == AbstractPower.PowerType.DEBUFF && p.hasRelic(WantedPoster.ID)) {
                WantedPoster wp = (WantedPoster) p.getRelic(WantedPoster.ID);
                wp.triggerRelic((CharacterEntity)target);
            }
        }
    }
}
package com.narxoz.rpg;

import com.narxoz.rpg.battle.RaidEngine;
import com.narxoz.rpg.battle.RaidResult;
import com.narxoz.rpg.bridge.AreaSkill;
import com.narxoz.rpg.bridge.FireEffect;
import com.narxoz.rpg.bridge.IceEffect;
import com.narxoz.rpg.bridge.ShadowEffect;
import com.narxoz.rpg.bridge.SingleTargetSkill;
import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.EnemyUnit;
import com.narxoz.rpg.composite.HeroUnit;
import com.narxoz.rpg.composite.PartyComposite;
import com.narxoz.rpg.composite.RaidGroup;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Homework 4 Demo: Bridge + Composite ===\n");

        // build leaves
        HeroUnit warrior = new HeroUnit("Arthas", 140, 30);
        HeroUnit mage = new HeroUnit("Jaina", 90, 40);
        EnemyUnit goblin = new EnemyUnit("Goblin", 70, 20);
        EnemyUnit orc = new EnemyUnit("Orc", 120, 25);
        EnemyUnit troll = new EnemyUnit("Troll", 150, 35);

        // build composite hierarchy (nested)
        PartyComposite heroes = new PartyComposite("Heroes");
        heroes.add(warrior);
        heroes.add(mage);

        PartyComposite frontline = new PartyComposite("Frontline");
        frontline.add(goblin);
        frontline.add(orc);

        PartyComposite backline = new PartyComposite("Backline");
        backline.add(troll);

        RaidGroup enemies = new RaidGroup("Enemy Raid");
        enemies.add(frontline);
        enemies.add(backline);

        System.out.println("--- Team Structures ---");
        heroes.printTree("");
        enemies.printTree("");

        // Bridge combinations
        System.out.println("\n--- Bridge Pattern Demonstration ---");
        System.out.println("1. Same skill with different effects:");

        Skill slashFire = new SingleTargetSkill("Slash", 20, new FireEffect());
        Skill slashIce = new SingleTargetSkill("Slash", 20, new IceEffect());
        Skill slashShadow = new SingleTargetSkill("Slash", 20, new ShadowEffect());

        System.out.println("   " + slashFire.getSkillName() + " + " + slashFire.getEffectName());
        System.out.println("   " + slashIce.getSkillName() + " + " + slashIce.getEffectName());
        System.out.println("   " + slashShadow.getSkillName() + " + " + slashShadow.getEffectName());

        System.out.println("\n2. Different skills sharing same effect:");

        Skill stormFire = new AreaSkill("Storm", 15, new FireEffect());
        Skill blastFire = new SingleTargetSkill("Blast", 25, new FireEffect());

        System.out.println("   " + stormFire.getSkillName() + " + " + stormFire.getEffectName());
        System.out.println("   " + blastFire.getSkillName() + " + " + blastFire.getEffectName());

        // run raid
        System.out.println("\n--- Raid Battle ---");
        RaidEngine engine = new RaidEngine().setRandomSeed(42L);
        RaidResult result = engine.runRaid(heroes, enemies, slashFire, stormFire);

        System.out.println("\n--- Raid Result ---");
        System.out.println("Winner: " + result.getWinner());
        System.out.println("Rounds: " + result.getRounds());
        System.out.println("\nBattle Log:");
        for (String line : result.getLog()) {
            System.out.println(line);
        }

        System.out.println("\n=== Demo Complete ===");
    }
}
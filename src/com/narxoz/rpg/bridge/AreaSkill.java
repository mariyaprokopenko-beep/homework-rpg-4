package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;
import java.util.List;

public class AreaSkill extends Skill {
    public AreaSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target) {
        if (target == null || !target.isAlive()) return;

        int damage = resolvedDamage();

        if (target.getChildren().isEmpty()) {
            target.takeDamage(damage);
            System.out.println(getSkillName() + " (" + getEffectName() + ") area hits " + target.getName() + " for " + damage);
        } else {
            List<CombatNode> children = target.getChildren();
            System.out.println(getSkillName() + " (" + getEffectName() + ") area affects " + target.getName() + "'s group:");
            for (CombatNode child : children) {
                if (child.isAlive()) {
                    child.takeDamage(damage);
                    System.out.println("  -> " + child.getName() + " takes " + damage + " damage");
                }
            }
        }
    }
}
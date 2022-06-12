package me.metropanties.springdiscordstarter.utils;

import lombok.experimental.UtilityClass;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import javax.annotation.Nonnull;
import java.util.Collection;

@UtilityClass
@SuppressWarnings("unused")
public class MemberUtils {

    public static boolean hasPermissions(@Nonnull Member member, @Nonnull Collection<Permission> permissions) {
        return member.hasPermission(permissions);
    }

}

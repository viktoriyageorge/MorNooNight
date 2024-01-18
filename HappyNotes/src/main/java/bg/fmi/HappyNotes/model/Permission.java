package bg.fmi.HappyNotes.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//This is Permission enum - we define permissions for three roles: USER, ADMIN and PREMIUM_USER. We use Lombok for required args constructor and getter for the permission field.
@RequiredArgsConstructor
public enum Permission {

      USER_READ("user:read"),
      USER_UPDATE("user:update"),
      USER_CREATE("user:create"),
      USER_DELETE("user:delete"),
      ADMIN_READ("admin:read"),
      ADMIN_UPDATE("admin:update"),
      ADMIN_CREATE("admin:create"),
      ADMIN_DELETE("admin:delete"),
      PREMIUM_USER_READ("premium_user:read"),
      PREMIUM_USER_UPDATE("premium_user:update"),
      PREMIUM_USER_CREATE("premium_user:create"),
      PREMIUM_USER_DELETE("premium_user:delete")

      ;

      @Getter
      private final String permission;
}

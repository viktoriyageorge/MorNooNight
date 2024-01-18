package bg.fmi.HappyNotes.model;

//This is Role enum - we define roles for three roles: USER, ADMIN and PREMIUM_USER. We use Lombok for required args constructor and getter for the list of permissions field.
//We also define a method getAuthorities() which returns a list of SimpleGrantedAuthority objects. We use the role name prefixed with ROLE_ as the authority name.
//We also add the permissions as authorities.
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.fmi.HappyNotes.model.Permission.ADMIN_CREATE;
import static bg.fmi.HappyNotes.model.Permission.ADMIN_DELETE;
import static bg.fmi.HappyNotes.model.Permission.ADMIN_READ;
import static bg.fmi.HappyNotes.model.Permission.ADMIN_UPDATE;
import static bg.fmi.HappyNotes.model.Permission.PREMIUM_USER_CREATE;
import static bg.fmi.HappyNotes.model.Permission.PREMIUM_USER_DELETE;
import static bg.fmi.HappyNotes.model.Permission.PREMIUM_USER_READ;
import static bg.fmi.HappyNotes.model.Permission.PREMIUM_USER_UPDATE;
import static bg.fmi.HappyNotes.model.Permission.USER_CREATE;
import static bg.fmi.HappyNotes.model.Permission.USER_DELETE;
import static bg.fmi.HappyNotes.model.Permission.USER_READ;
import static bg.fmi.HappyNotes.model.Permission.USER_UPDATE;

@RequiredArgsConstructor
public enum Role {

  USER(Set.of(
          USER_READ,
          USER_UPDATE,
          USER_DELETE,
          USER_CREATE
  )),
  ADMIN(
          Set.of(
                  ADMIN_READ,
                  ADMIN_UPDATE,
                  ADMIN_DELETE,
                  ADMIN_CREATE,
                  USER_READ,
                  USER_UPDATE,
                  USER_DELETE,
                  USER_CREATE,
                  PREMIUM_USER_READ,
                  PREMIUM_USER_UPDATE,
                  PREMIUM_USER_DELETE,
                  PREMIUM_USER_CREATE
          )
  ),
  PREMIUM_USER(
          Set.of(
                  PREMIUM_USER_READ,
                  PREMIUM_USER_UPDATE,
                  PREMIUM_USER_DELETE,
                  PREMIUM_USER_CREATE,
                  USER_READ,
                  USER_UPDATE,
                  USER_DELETE,
                  USER_CREATE
          )
  )

  ;

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}


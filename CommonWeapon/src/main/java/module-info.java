import dk.sdu.sesp.geight.common.services.ICollidable;
import dk.sdu.sesp.geight.common.services.collision.IBullet;
import dk.sdu.sesp.geight.common.weapon.bullet.BulletSPI;

module CommonWeapon {
    exports dk.sdu.sesp.geight.common.weapon;
    exports dk.sdu.sesp.geight.common.weapon.bullet;
    requires com.badlogic.gdx;
    requires Common;
    uses BulletSPI;
    provides IBullet with dk.sdu.sesp.geight.common.weapon.bullet.Bullet;
    provides ICollidable with dk.sdu.sesp.geight.common.weapon.bullet.Bullet;
}
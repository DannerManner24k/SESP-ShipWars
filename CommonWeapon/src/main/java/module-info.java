import dk.sdu.sesp.geight.common.weapon.bullet.BulletSPI;

module CommonWeapon {
    exports dk.sdu.sesp.geight.common.weapon;
    exports dk.sdu.sesp.geight.common.weapon.bullet;
    requires com.badlogic.gdx;
    requires Common;
    uses BulletSPI;
}
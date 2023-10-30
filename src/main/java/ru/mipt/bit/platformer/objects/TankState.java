package ru.mipt.bit.platformer.objects;

public class TankState {
    private int health;
    private int curHealth;
    private Tank tank;

    public TankState(int health) {
        this.health = health;
        this.curHealth = health;
    }
    public TankState(){
        this.health = curHealth = 10;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public void receiveDamage(Bullet bullet) {
        curHealth -= bullet.getDamage();
        if (curHealth < health / 2) {
            //
        }
    }
}

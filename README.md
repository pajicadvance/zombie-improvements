# Zombie Improvements

This mod improves zombie leader and reinforcement mechanics and fixes a few bugs with them.

## What?

If you didn't even know these were a thing, here's a rundown from the Minecraft wiki:

> On all difficulty levels, damaged zombie mobs (including husks, drowned and even zombified piglins) call all other zombies within a 67×21×67 to 111×21×111 area centered on the attacked zombie to target the attacker.
> 
> In Hard difficulty, zombies can spawn additional zombies to "help" when damaged. Each zombie has a "likeliness to call reinforcements" statistic ranging from 0–10%, and "leader" zombies (0–5% depending on regional difficulty) get a bonus of 50–75 percentage points to the stat.
> 
> On Hard difficulty, zombies have a 5% chance to spawn as leaders. Leader zombies can spawn other zombies as reinforcements.

## So what does this mod do?

There are a few issues with these mechanics:
- You can't tell if the zombie you're attacking is a leader or not.
- There is no visual/audio cue when zombies spawn additional reinforcements.
- There are a few bugs which make these mechanics less relevant.

This mod alleviates all of these issues by:
- Adding a red aura effect around leader zombies, identical to the charged creeper effect, but red color.
- Adding a sound that plays when a zombie spawns in additional reinforcements. The sound that will be played is the block breaking sound of the block the freshly spawned zombie is standing on, played 5 times in a span of 2 seconds.
- Fixing two vanilla bugs, namely [MC-219981](https://bugs.mojang.com/browse/MC/issues/MC-219981) and (1.21.1 only) [MC-14800](https://bugs.mojang.com/browse/MC/issues/MC-14800).

There's also a few additional tweaks, disabled by default:
- Allow only leader zombies to spawn additional reinforcements.
- Prevent mob spawners from spawning leaders.
- Prevent zombies spawned by mob spawners from spawning additional reinforcements.
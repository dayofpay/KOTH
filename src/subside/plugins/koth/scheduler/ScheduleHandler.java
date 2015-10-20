package subside.plugins.koth.scheduler;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import subside.plugins.koth.adapter.Koth;
import subside.plugins.koth.loaders.ScheduleLoader;

public class ScheduleHandler {
    private static @Getter ScheduleHandler instance = new ScheduleHandler();
    private @Getter List<Schedule> schedules = new ArrayList<>();

    public Schedule getNextEvent() {
        Schedule ret = null;
        for (Schedule sched : schedules) {
            if (ret == null) {
                ret = sched;
            } else if (sched.getNextEvent() < ret.getNextEvent()) {
                ret = sched;
            }
        }
        return ret;
    }

    public Schedule getNextEvent(Koth koth) {
        Schedule ret = null;
        for (Schedule sched : schedules) {
            if (sched.getKoth().equalsIgnoreCase(koth.getName())) {
                if (ret == null) {
                    ret = sched;
                } else if (sched.getNextEvent() < ret.getNextEvent()) {
                    ret = sched;
                }
            }
        }
        return ret;
    }

    public String removeId(int id) {
        Schedule sched = schedules.get(id);
        if (sched == null) {
            return null;
        }

        schedules.remove(id);
        ScheduleLoader.save();
        return sched.getKoth();
    }

    public void tick() {
        for (Schedule schedule : schedules) {
            schedule.tick();
        }
    }
}

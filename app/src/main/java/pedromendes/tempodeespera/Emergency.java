package pedromendes.tempodeespera;


public class Emergency {
    String name;
    String description;
    EmergencyQueue redQueue = new EmergencyQueue();
    EmergencyQueue orangeQueue = new EmergencyQueue();
    EmergencyQueue yellowQueue = new EmergencyQueue();
    EmergencyQueue greenQueue = new EmergencyQueue();
    EmergencyQueue blueQueue = new EmergencyQueue();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EmergencyQueue getRedQueue() {
        return redQueue;
    }

    public void setRedQueue(int time, int length) {
        this.redQueue = new EmergencyQueue(time, length);
    }

    public EmergencyQueue getOrangeQueue() {
        return orangeQueue;
    }

    public void setOrangeQueue(int time, int length) {
        this.orangeQueue = new EmergencyQueue(time, length);
    }

    public EmergencyQueue getYellowQueue() {
        return yellowQueue;
    }

    public void setYellowQueue(int time, int length) {
        this.yellowQueue = new EmergencyQueue(time, length);
    }

    public EmergencyQueue getGreenQueue() {
        return greenQueue;
    }

    public void setGreenQueue(int time, int length) {
        this.greenQueue = new EmergencyQueue(time, length);
    }

    public EmergencyQueue getBlueQueue() {
        return blueQueue;
    }

    public void setBlueQueue(int time, int length) {
        this.blueQueue = new EmergencyQueue(time, length);
    }

    public void setRedQueue(EmergencyQueue queue) {
        this.redQueue = queue;
    }

    public void setOrangeQueue(EmergencyQueue queue) {
        this.orangeQueue = queue;
    }

    public void setYellowQueue(EmergencyQueue queue) {
        this.yellowQueue = queue;
    }

    public void setGreenQueue(EmergencyQueue queue) {
        this.greenQueue = queue;
    }

    public void setBlueQueue(EmergencyQueue queue) {
        this.blueQueue = queue;
    }

    static class EmergencyQueue {
        int time;
        int length;

        public EmergencyQueue() {
        }

        public EmergencyQueue(int time, int length) {
            this.time = time;
            this.length = length;
        }

        public String getTime() {
            if(time > 0) {
                return "" +  (time / 60) + " min";
            }

            return "-";
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }


    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName() + "\n" );
        sb.append(getDescription() + "\n" );
        sb.append("Senha verde: " + getGreenQueue().getTime() + "\n" );
        sb.append("Senha azul: " + getBlueQueue().getTime() + "\n" );
        sb.append("Senha amarela: " + getYellowQueue().getTime() + "\n" );
        sb.append("Senha laranja: " + getOrangeQueue().getTime() + "\n" );
        sb.append("Senha vermelha: " + getRedQueue().getTime() + "\n" );

        return sb.toString();
    }
}

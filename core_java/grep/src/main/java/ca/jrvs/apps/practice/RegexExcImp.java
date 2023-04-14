package ca.jrvs.apps.practice;

public class RegexExcImp implements RegexExc {
    @Override
    public boolean matchJpeg(String filename) {
        return filename.matches("([^\\s]+(\\.(?i)(jpg|jpeg))$)");
    }

    @Override
    public boolean matchIp(String ip) {
        return ip.matches("\\d{1,3}.\\d{1,3}.\\d{1,3}");
    }

    @Override
    public boolean isEmptyLine(String line) {
        return line.matches("^\\s*$");
    }

    public static void main(String[] args) {
        RegexExcImp regexExcImp = new RegexExcImp();
        System.out.println(regexExcImp.matchJpeg("fafa.jpg"));
        System.out.println( regexExcImp.matchIp("0.0"));
        System.out.println(regexExcImp.isEmptyLine(""));
    }
}



import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Weather {
	
	private int locationCode;
    //private String date;
    //private String time;
    private double PTY; //강수형태 코드값
    private double REH; //습도 %
    private double RN1; //1시간 강수량 범주(1mm)
    private double T1H; //기온 ℃
    private double UUU; //풍속(동서성분) m/s
    private double VVV; //풍속(남북성분) m/s
    private double VEC; //풍향 deg
    private double WSD; //풍속 m/s
    
    private String PTYForm; //(초단기) 없음(0), 비(1), 비/눈(2), 눈(3), 빗방울(5), 빗방울눈날림(6), 눈날림(7) 
							//(단기) 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4)
    public String setPTYForm(double PTY) {
    if(PTY==0) {
    	PTYForm="맑음"; //강수형태 없음
    }
    else if(PTY==1) {
    	PTYForm="비";
    }
    else if(PTY==2) {
    	PTYForm="비/눈";
    }
    else if(PTY==3) {
    	PTYForm="눈";
    }
    else if(PTY==4) {
    	PTYForm="소나기";
    }
    else if(PTY==5) {
    	PTYForm="빗방울";
    }
    else if(PTY==6) {
    	PTYForm="빗방울눈날림";
    }
    else if(PTY==7) {
    	PTYForm="눈날림";
    }
    else
    	PTYForm="error";
	return PTYForm;
    }
    
    LocalDate date = LocalDate.now();
	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
	
	LocalTime time = LocalTime.now();
	DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH시 mm분");
	

    @Override
    public String toString() {
        return  "locationCode = " + locationCode +
                "\n날짜 = " + date.format(formatter1)+
                "\n시간 = " + time.format(formatter2) +
                "\n오늘의 날씨 = " +setPTYForm(PTY)+
                "\n습도 = " + REH +"(%)"+
                "\n강수량 = " + RN1 +"(mm)"+
                "\n기온 = " + T1H +"(℃)"+
                "\n풍속(동서) = " + UUU +"(m/s)"+
                "\n풍속(남북) = " + VEC +"(m/s)"+
                "\n풍향 = " + VVV +"(deg)"+
                "\n풍속 = " + WSD +"(m/s)";
    }
    
    public String toStringdate() {
        return date.format(formatter1);       
    }
    public String toStringPTY() {
        return "\n오늘의 날씨 : " +setPTYForm(PTY);
    }
    public String toStringREH() {
        return "\n습도 : " + REH +"(%)";
    }
    public String toStringRN1() {
        return "\n강수량 : " + RN1 +"(mm)";
    }
    public String toStringT1H() {
        return "\n기온 : " + T1H +"(℃)";
    }

	public void setPTY(double PTY) {
		this.PTY = PTY;
	}

	public void setREH(double REH) {
		this.REH = REH;
	}

	public void setRN1(double RN1) {
		this.RN1 = RN1;
	}

	public void setT1H(double T1H) {
		this.T1H = T1H;
	}

	public void setUUU(double UUU) {
		this.UUU = UUU;
	}

	public void setVEC(double VEC) {
		this.VEC = VEC;
	}

	public void setVVV(double VVV) {
		this.VVV = VVV;
	}

	public void setWSD(double WSD) {
		this.WSD = WSD;
	}
}

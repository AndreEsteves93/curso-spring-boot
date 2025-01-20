package com.cursospring.arquiteturaspring.montadora.configuration;

import com.cursospring.arquiteturaspring.montadora.Motor;
import com.cursospring.arquiteturaspring.montadora.TipoMotor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MontadoraConfiguration {

    @Bean(name = "motorAspirado")
    public Motor motorAspirado() {
        var motor = new Motor();
        motor.setCavalos(120);
        motor.setCilindros(4);
        motor.setModelo("XPTO-0");
        motor.setLitragem(2.0);
        motor.setTipo(TipoMotor.ASPIRADO);
        return motor;
    }

    @Bean(name = "motorEletrico")
    public Motor motorEletrico() {
        var motor = new Motor();
        motor.setCavalos(110);
        motor.setCilindros(3);
        motor.setModelo("TH-40");
        motor.setLitragem(1.4);
        motor.setTipo(TipoMotor.ELETRICO);
        return motor;
    }

    @Bean(name = "motorTurbo")
    @Primary
    public Motor motorTurbo() {
        var motor = new Motor();
        motor.setCavalos(180);
        motor.setCilindros(4);
        motor.setModelo("XPTO-1");
        motor.setLitragem(1.5);
        motor.setTipo(TipoMotor.TURBO);
        return motor;
    }
}

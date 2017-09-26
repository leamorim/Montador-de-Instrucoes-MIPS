////////////////////////////////////////////////////////////////////////
/*
			ESTE CODIGO FOI DESENVOLVIDO PARA FACILITAR OS TESTES DO PROJETO DE HARDWARE
			
			A ENTRADA DEVERA SER UM CODIGO EM ASSEMBLY IGNORANDO ','  '('  ')'  '.'
		
			EXEMPLO DE ENTRADA
			
			7//NUMERO DE INSTRUÇOES
			mfhi 1
			mflo 2
			add 1 30 1
			addu 2 29 2
			and 1 12 1
			jr 2
			mult 12 10
			
*/
///////////////////////////////////////////////////////////////////////


import java.util.ArrayList;
import java.util.List;

public class Montador {
	
	int numero_de_intrucoes;
	
	String instrucao,opcode,funcao;
	int RS,RD,RT,shamt,IMM,offset;
	Arquivo io = new Arquivo("Assembly.txt","instrucoes.mif");
	
	
	public class Estrutura{
		String assembly;
		String binario;
		public Estrutura(String assembly,String binario) {
			this.assembly = assembly;
			this.binario = binario;
		}
		
		public boolean equals(Object ob) {
			boolean retorno = false;
			
			if (ob instanceof Estrutura) {
				retorno = this.assembly.equalsIgnoreCase(((Estrutura) ob).assembly);
			}
			
			return retorno;
		}
	}
	
	public class TipoR{
		
		List<Estrutura> OPCODE = new ArrayList<Estrutura>();
		List<Estrutura> FUNCT = new ArrayList<Estrutura>();
		
		
		public TipoR() {
			
                    OPCODE.add(new Estrutura("add","000000"));
                    OPCODE.add(new Estrutura("and","000000"));
                    OPCODE.add(new Estrutura("jr","000000"));
                    OPCODE.add(new Estrutura("mfhi","000000"));
                    OPCODE.add(new Estrutura("mflo","000000"));
                    OPCODE.add(new Estrutura("sll","000000"));
                    OPCODE.add(new Estrutura("sllv","000000"));
                    OPCODE.add(new Estrutura("slt","000000"));
                    OPCODE.add(new Estrutura("sra","000000"));
                    OPCODE.add(new Estrutura("srav","000000"));
                    OPCODE.add(new Estrutura("srl","000000"));
                    OPCODE.add(new Estrutura("sub","000000"));
                    OPCODE.add(new Estrutura("break","000000"));
                    OPCODE.add(new Estrutura("rte","010000"));
                    OPCODE.add(new Estrutura("mult","000000"));
                    OPCODE.add(new Estrutura("push","000000"));
                    OPCODE.add(new Estrutura("nop","000000"));
                    OPCODE.add(new Estrutura("addu","000000"));
                    OPCODE.add(new Estrutura("subu","000000"));
                    OPCODE.add(new Estrutura("xor","000000"));
                        

            //FUNÇÃO
                FUNCT.add(new Estrutura("add","100000"));
                FUNCT.add(new Estrutura("and","100100"));
                FUNCT.add(new Estrutura("jr","001000"));
                FUNCT.add(new Estrutura("mfhi","010000"));
                FUNCT.add(new Estrutura("mflo","010010"));
                FUNCT.add(new Estrutura("sll","000000"));
                FUNCT.add(new Estrutura("sllv","000100"));
                FUNCT.add(new Estrutura("slt","101010"));
                FUNCT.add(new Estrutura("sra","000011"));
                FUNCT.add(new Estrutura("srav","000111"));
                FUNCT.add(new Estrutura("srl","000010"));
                FUNCT.add(new Estrutura("sub","100010"));
                FUNCT.add(new Estrutura("break","001101"));
                FUNCT.add(new Estrutura("rte","010000"));
                FUNCT.add(new Estrutura("mult", "011000"));
                FUNCT.add(new Estrutura("push", "000101"));
                FUNCT.add(new Estrutura("nop","000000"));
                FUNCT.add(new Estrutura("addu","100001"));
                FUNCT.add(new Estrutura("subu","100011"));
                FUNCT.add(new Estrutura("xor","100110"));                        
		}
	}

	public class TipoI {
		
		List<Estrutura> OPCODE = new ArrayList<Estrutura>();
		
		public TipoI() {
			
			OPCODE.add(new Estrutura("addi","001000"));
			OPCODE.add(new Estrutura("addiu","001001"));
			OPCODE.add(new Estrutura("beq","000100"));
			OPCODE.add(new Estrutura("bne","000101"));
			OPCODE.add(new Estrutura("ble","000110"));
			OPCODE.add(new Estrutura("bgt","000111"));
			OPCODE.add(new Estrutura("beqmm","000001"));
			OPCODE.add(new Estrutura("lbu","100100"));
			OPCODE.add(new Estrutura("lhu","100101"));
			OPCODE.add(new Estrutura("lui","001111"));
			OPCODE.add(new Estrutura("lw","100011"));
			OPCODE.add(new Estrutura("sb","101000"));
			OPCODE.add(new Estrutura("sh","101001"));
			OPCODE.add(new Estrutura("slti","001010"));
			OPCODE.add(new Estrutura("sw","101011"));
                        OPCODE.add(new Estrutura("andi","001100"));
                        OPCODE.add(new Estrutura("sxori","001110"));
			
		}
	}
	
	public class TipoJ {
		
		List<Estrutura> OPCODE = new ArrayList<Estrutura>();
		
		public TipoJ() {
			
			OPCODE.add(new Estrutura("j","000010"));
			OPCODE.add(new Estrutura("jal","000011"));
			
		}		
	}
	
	TipoR R = new TipoR();
	TipoI I = new TipoI();
	TipoJ J = new TipoJ();
	
	public Montador() {
		numero_de_intrucoes = 0;
                System.out.println("convertendo ...");
		io.println("WIDTH = 8;");
                io.println("DEPTH = " + "256;");
                io.println();
                io.println("ADDRESS_RADIX = DEC;");
                io.println("DATA_RADIX = BIN;");
                io.println();
                io.println("CONTENT");
                io.println();
                io.println("BEGIN");
		int contador = 64;
		while(!io.isEndOfFile()) {
			instrucao = io.readString();
			
			int iR = R.OPCODE.indexOf(new Estrutura(instrucao, null));
			int iI = I.OPCODE.indexOf(new Estrutura(instrucao, null));
			int iJ = J.OPCODE.indexOf(new Estrutura(instrucao, null));
			
			if (iR != -1)
				processo_R(iR);
			
			if (iI != -1)
				processo_I(iI);
			
			if (iJ != -1)
				processo_J(iJ);
                        
                        contador --;
		}
                
		while(contador >= 0){
                    
                    if(contador == 6){
                         imprimir("00100000000111100000000000000001","excecao");
                    }
                    else if(contador == 5){
                        imprimir("00000000000000000000000000001101","excecao");
                    }
                    else if(contador == 3){
                        imprimir("00111100000111100000000000000010","excecao");
                    }
                    else if(contador == 2){
                        imprimir("00000000000111101111010000000010","excecao");
                    }
                    else if(contador == 1){
                        imprimir("00000000000000000000000000001101","excecao");
                    }
                    else if(contador == 0){
                        imprimir("00000000000000001111000011100100","excecao");
                    }
                    else{
                        imprimir("00000000000000000000000000000000","nop");
                    }
                    contador --;
                }
		io.println("END;");
		io.close();
                System.out.println("Convertido com sucesso !");
                System.out.println("Infra de Hardware EC 2017.1 ;)");
	}
	
	void processo_R(int i){
		
		String assembly = "";
		
		opcode = R.OPCODE.get(i).binario;
		funcao = R.FUNCT.get(i).binario;
	
		assembly = R.FUNCT.get(i).assembly+" ";

		if (i == 0 || i == 1 || i == 7 || i == 11 ||i == 17 ||  i == 18 || i == 19) {
                    // add, and, slt, sub, addu, subu, xor
			RD = io.readInt();
			RS = io.readInt();
			RT = io.readInt();
			shamt = 0;
			assembly += RD+" "+RS+" "+RT;
		}else if(i == 6 || i == 9){// sllv,srav
			RD = io.readInt();
			RT = io.readInt();
			RS = io.readInt();
			shamt = 0;
			assembly += RD+" "+RT+" "+RS;
		} 
		else if (i == 2) { // jr
			RD = 0;
			RT = 0;
			RS = io.readInt();
			shamt = 0;
			assembly += RS;
		} else if (i == 3 || i == 4) { // mfhi, mflo
			RD = io.readInt();
			RT = 0;
			RS = 0;
			shamt = 0;
			assembly += RD;
		} else if (i == 14) { //mult
			RS = io.readInt();
			RT = io.readInt();
			RD = 0;
			shamt = 0;
			assembly += RS+" "+RT;
		} else if (i == 5 || i == 8 || i == 10) { // sll, sra, srl
			RD = io.readInt();
			RT = io.readInt();
			RS = 0;
			shamt = io.readInt();
			assembly += RD+" "+RT+" "+ shamt;
		} 	else { // break, rte, nop
			RD = 0;
			RT = 0;
			RS = 0;
			shamt = 0;
		}
		
		imprimir(formata_R(RS, RT, RD, shamt, opcode, funcao),assembly);
	}
	
	void processo_I(int i){
		String assembly = "";
		
		opcode = I.OPCODE.get(i).binario;
		assembly += I.OPCODE.get(i).assembly + " ";
		
		
		if (i == 0|| i == 1 || i == 13 || i == 15 || i == 16) { // addi, addiu, slti, andi
			RT = io.readInt();
			RS = io.readInt();
			IMM = io.readInt();
			assembly += RT+" " + RS+" " + IMM;
		} else if (i == 2|| i == 3 || i == 4 || i == 5 || i == 6) { // beq, bne, ble, bgt, beqmm
			RS = io.readInt();
			RT = io.readInt();
			IMM = io.readInt();
			assembly += RS+" " + RT+" " + IMM;
		} else if (i == 9) { // lui
			RT = io.readInt();
			RS = 0;
			IMM = io.readInt();
			assembly += RT+" "+ IMM;
		} else { // lb, lh, lw, sb, sh, sw
			RT = io.readInt();
			IMM = io.readInt();
			RS = io.readInt();
			assembly += RT+" " + IMM+" (" + RS+")";
		}
		imprimir(formata_I(RS,RT,IMM,opcode),assembly);
	}
	
	void processo_J(int i){
		
		opcode = J.OPCODE.get(i).binario;
		offset = io.readInt();
		
		imprimir(formata_J(opcode, offset),(J.OPCODE.get(i).assembly +" "+ offset));
	}

	String formata_I(int rs,int rt,int imm,String OPCODE){
		String codigo = "";
		codigo += OPCODE;
		codigo += retorna_bits(rs,5);
		codigo += retorna_bits(rt,5);
		codigo += retorna_bits(imm,16);
		return codigo;
	}
	
	String formata_R(int rs,int rt,int rd, int SHAMT,String OPCODE, String FUNCAO){
		String codigo = "";
		codigo += OPCODE;
		codigo += retorna_bits(rs,5);
		codigo += retorna_bits(rt,5);
		codigo += retorna_bits(rd,5);
		codigo += retorna_bits(SHAMT,5);
		codigo += FUNCAO;
		return codigo;
	}

	String formata_J(String OPCODE,int OFFSET) {
		String codigo = OPCODE;
		codigo += retorna_bits(OFFSET, 26);
		return codigo;
	}
	
	String retorna_bits(int i, int tamanho) {
		String temp = Integer.toBinaryString(i);
                    while(temp.length() < tamanho){
                            temp = "0" + temp;
                    }
                if(i < 0){
                    temp = temp.substring(16);
                }    
                return temp;
	}
	
	String retorna_formatacao() {
		String temp = Integer.toString(numero_de_intrucoes);
		
		while(temp.length() < 3) {
			temp = "0" + temp;
		}
		
		numero_de_intrucoes++;
		return temp;
	}
	
	void imprimir(String codigo, String assembly) {
		StringBuffer temp = new StringBuffer(codigo);
                io.println();
		io.println(retorna_formatacao()+": "+temp.substring(0,8)+"; --"+assembly);
		io.println(retorna_formatacao()+": "+temp.substring(8,16)+";");
		io.println(retorna_formatacao()+": "+temp.substring(16,24)+";");
		io.println(retorna_formatacao()+": "+temp.substring(24,32)+";");
		io.println();
	}

	public static void main(String[] args) {
		new Montador();
	}
}

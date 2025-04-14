#Grupo 4
#Cássio Montenegro Marques
#Erik Calixto
#Gabriel Campos Luiz de Mendonça
#Marcos Paulo do Nascimento

import pygame
from pygame.locals import *
import random

Arquivo = open("Assets/Imagens/Recorde.txt", "r")
RecordeAnterior = int(Arquivo.readline().strip())

pygame.init()

clock = pygame.time.Clock()
fps = 60

ATela = 640
LTela = 480

#Define font
font = pygame.font.SysFont('Koulen', 80)
fontint = pygame.font.SysFont('Koulen', 40)

#Define cor
red = (255, 0, 0)

#Variáveis do jogo
ChaoX = 0
ChaoVel = 4
Voar = False
GameOver = False
EspacoArvore = 130
FrequenciaArvore = 1400 #milisegundos
UltimaArvore = pygame.time.get_ticks() - FrequenciaArvore
Pontos = 0
PassouArvore = False
Recorde = RecordeAnterior
XTexto = 10
XTextoI = 10
Tela = 1
Colidir = True
Contador = 0

#Som
SomC = pygame.mixer.Sound("Assets/Sons/Colisão.mp3")
SomC.set_volume(1.0)
SomP = pygame.mixer.Sound("Assets/Sons/Ponto.wav")
SomP.set_volume(0.5)

#Definição da tela
TelaDoJogo = pygame.display.set_mode((LTela, ATela))
pygame.display.set_caption("Juan Saltitante")


#Imagens 
TelaFundo = pygame.image.load("Assets/Imagens/Menu.png")
BotaoRestart = pygame.image.load("Assets/Imagens/BotRestart.png")
BotaoJogar = pygame.image.load('Assets/Imagens/BotJogar.png')
Botaocred = pygame.image.load("Assets/Imagens/BotCred.png")
BotaoVolt = pygame.image.load("Assets/Imagens/BotVoltar.png")
BotaoSair = pygame.image.load("Assets/Imagens/BotSair.png")
Fundo1 = pygame.image.load('Assets/Imagens/Fundo.png')
Fundo2 = pygame.image.load('Assets/Imagens/Fundo2.png')
Fundo3 = pygame.image.load('Assets/Imagens/Fundo3.png')
Fundo4 = pygame.image.load('Assets/Imagens/Fundo4.png')
ChaoFundo = pygame.image.load("Assets/Imagens/Chão.png")
Chao1 = pygame.image.load("Assets/Imagens/Chão.png")
Chao2 = pygame.image.load("Assets/Imagens/Chão2.png")
Chao3 = pygame.image.load("Assets/Imagens/Chão3.png")
Chao4 = pygame.image.load("Assets/Imagens/Chão4.png")
FundoC = pygame.image.load("Assets/Imagens/Créditos.png")
FundoM = pygame.image.load("Assets/Imagens/Menu.png")

def DesenhaTexto(text, font, textCol, x, y):
    img = font.render(text, True, textCol)
    TelaDoJogo.blit(img, (x, y))

def ResetaJogo():
    GrupoArvore.empty()
    InicioJuan.rect.x = 100
    InicioJuan.rect.y = int(ATela/2)
    Pontos = 0
    return Pontos 

class Juan(pygame.sprite.Sprite):
    def __init__(self, x, y):
        pygame.sprite.Sprite.__init__(self)
        self.images = []
        self.index = 0
        self.counter = 0

        #Adiciona Juan's
        for num in range(1, 4):
            Juans = pygame.image.load(f"Assets/Imagens/Juan{num}.png")
            self.images.append(Juans)
        
        self.image = self.images[self.index]
        self.rect = self.image.get_rect()
        self.rect.center = [x,y]
        self.vel = 0

    def update(self):
        #Gravidade
        if Voar == True:
            self.vel += 0.5

            if self.vel > 8:
                self.vel = 8

            if self.rect.bottom < 480:
                self.rect.y += int(self.vel)

        if Voar == False:
            DesenhaTexto(str('Pressione espaço para começar'), fontint, red, int(LTela / 2 - 205), 170)
            
        if GameOver == False:
            #Pulo
            if pygame.key.get_pressed()[K_SPACE] == 1 and self.clicked == False:
                self.clicked = True
                self.vel = -8.2

            if pygame.key.get_pressed()[K_SPACE] == 0:
                self.clicked = False
            
            #Animação
            self.counter += 1
            TempoImagem = 5

            if self.counter > TempoImagem:
                self.counter = 0
                self.index += 1
                if self.index >= len(self.images):
                    self.index = 0
                    
            self.image = self.images[self.index]
            

            #Girar Juan
            self.image = pygame.transform.rotate(self.images[self.index], self.vel * -2)
        else:
            self.image = pygame.transform.rotate(self.images[self.index], -90)

class Arvore(pygame.sprite.Sprite):
    def __init__(self, x, y, position):
        pygame.sprite.Sprite.__init__(self)
        self.image = pygame.image.load("Assets/Imagens/Árvore.png")
        if GameOver == True:
            if botao.draw() == True:
                 self.image = pygame.image.load("Assets/Imagens/Árvore.png")
        if Contador >= 5 and Contador <=9:
            self.image = pygame.image.load("Assets/Imagens/Árvore2.png")
        if Contador >= 10 and Contador <=14:
            self.image = pygame.image.load("Assets/Imagens/Árvore3.png")
        if Contador >= 15 and Contador <=19:
            self.image = pygame.image.load("Assets/Imagens/Árvore4.png")
            
        self.rect = self.image.get_rect()

        #Position 1 pro topo e -1 pro chão
        if position == 1:
            self.image = pygame.transform.flip(self.image, False, True)
            self.rect.bottomleft = [x, y - int(EspacoArvore // 2)]

        if position == -1:
            self.rect.topleft = [x, y + int(EspacoArvore // 2)]

    #Movimento Árvore
    def update(self):
        self.rect.x -= ChaoVel
        if self.rect.right < 0:
            self.kill()
        
class Botao():
    def __init__(self, x, y, image):
        self.image = image
        self.rect = self.image.get_rect()
        self.rect.topleft = (x, y)

    def draw(self):
        Acao = False
        
        #Posição do mouse
        pos = pygame.mouse.get_pos()

        #Se mouse estiver no botão
        if self.rect.collidepoint(pos):
            if pygame.mouse.get_pressed()[0] == 1:
                Acao = True
    
        #Desenha botão
        TelaDoJogo.blit(self.image, (self.rect.x, self.rect.y))

        return Acao

#Cria grupos
GrupoJuan = pygame.sprite.Group()
GrupoArvore = pygame.sprite.Group()

InicioJuan = Juan(100, int(ATela/2))

GrupoJuan.add(InicioJuan)


#Define botão
botaorestart = Botao(LTela // 2 - 108, ATela //2 - 30, BotaoRestart)
botaojogar = Botao(LTela // 2 - 103, ATela // 2 - 90, BotaoJogar)
botaocred = Botao(LTela // 2 - 103, ATela // 2 + 10, Botaocred)
botaovolt = Botao(LTela // 2 - 233, ATela // 2 + 185, BotaoVolt)
botaosair = Botao(LTela // 2 - 103, ATela // 2 + 110, BotaoSair)
botaosairII = Botao(LTela // 2 - 108, ATela // 2 + 110, BotaoSair)

Jogando = True
while Jogando:
    TelaDoJogo.blit(TelaFundo, (0, 0))
    if Tela == 1:
        if botaojogar.draw() == True:
            Tela = 2
        if botaocred.draw() == True:
            Tela = 3
        if botaosair.draw() == True:
            Jogando = False
        
    if Tela == 3:
        TelaFundo = FundoC
        TelaDoJogo.blit(TelaFundo, (0, 0))
        if botaovolt.draw() == True:
            Tela = 1
            TelaFundo = FundoM

    if Tela == 2:
        clock.tick(fps)
        
        #Tela de fundo
        TelaFundo = Fundo1

        #Juan
        GrupoJuan.draw(TelaDoJogo)
        GrupoJuan.update()

        #Árvore
        GrupoArvore.draw(TelaDoJogo)

        #Chão
        TelaDoJogo.blit(ChaoFundo, (ChaoX,485))
        ChaoFundo = Chao1


        #Troca de fase
         
        if Contador >= 5 and Contador <=9:
            TelaFundo = Fundo2
            ChaoFundo = Chao2

        if Contador >= 10 and Contador <=14:
            TelaFundo = Fundo3
            ChaoFundo = Chao3

        if Contador >= 15 and Contador <=19:
            TelaFundo = Fundo4
            ChaoFundo = Chao4
        if Contador == 20:
            Contador = 0

        #Verifica os pontos
        if len(GrupoArvore) > 0:
            if GrupoJuan.sprites()[0].rect.left > GrupoArvore.sprites()[0].rect.left\
                and GrupoJuan.sprites()[0].rect.right < GrupoArvore.sprites()[0].rect.right\
                and PassouArvore == False:
                PassouArvore = True

            if PassouArvore == True:
                if GrupoJuan.sprites()[0].rect.left > GrupoArvore.sprites()[0].rect.right:
                    Pontos += 1
                    Contador += 1
                    SomP.play()
                    PassouArvore = False          

        #Texto      
        DesenhaTexto(str(Pontos), font, red, int(LTela / 2 - XTexto), 20)
        if Pontos >= 10 and Pontos <= 99:
            XTexto = 27
        elif Pontos >= 100 and Pontos <= 999:
            XTexto = 40
        else:
            XTexto = 10
            
        if Pontos == 999:
            GameOver = True
            Colidir = False
            DesenhaTexto(str('Você venceu!'), font, red, int(LTela / 2 - 180), 180)
        
        #Colisão 
        if Colidir == True:
            #Colisão entre Árvore e Juan
            if pygame.sprite.groupcollide(GrupoJuan, GrupoArvore, False, False) or InicioJuan.rect.top < 0:
                GameOver = True
                SomC.play(0)
                Colidir = False
        
            #Colisão se bate no chão
            if InicioJuan.rect.bottom >= 480:
                GameOver = True
                ChaoX = 0
                SomC.play()
                Colidir = False
        
        if GameOver == False and Voar == True:
            #Gera nova Árvore
            TempoAgora = pygame.time.get_ticks()
            if TempoAgora - UltimaArvore > FrequenciaArvore:
                AArvore = random.randint(-100, 100)
                BotaArvoreC = Arvore(LTela, int(ATela/2 - 20)+ AArvore, 1)
                BotaArvoreB = Arvore(LTela, int(ATela/2 - 20)+ AArvore, -1)
                GrupoArvore.add(BotaArvoreC)
                GrupoArvore.add(BotaArvoreB)
                UltimaArvore = TempoAgora
                
            #Chão movimentando
            ChaoX -= ChaoVel
            if abs(ChaoX) > 70:
                ChaoX = 0

            #Atualiza Árvore
            GrupoArvore.update()
               
        #Game over e reset
        if GameOver == True:
            if botaorestart.draw() == True:
                Colidir = True
                GameOver = False
                Voar = False
                Pontos = ResetaJogo()
                TelaFundo = Fundo1
                ChaoFundo = Chao1
                Contador = 0
                

            if botaosairII.draw() == True:
                Jogando = False

            DesenhaTexto(str('------'), font, red, int(LTela / 2 - 50), 45)
            DesenhaTexto(str(Recorde), font, red, int(LTela / 2 - XTextoI), 80)
            if Recorde < Pontos:
                Recorde = Pontos
                Arquivo = open("Assets/Imagens/Recorde.txt", "w")
                Arquivo.write(str(Recorde))
            if Recorde >= 10:
                XTextoI = 27
            if Recorde >= 100:
                XTextoI = 40
        
    for event in pygame.event.get():
        if event.type == QUIT:
            Jogando = False

        if event.type == KEYDOWN:
            if event.key == K_SPACE and Voar == False and GameOver == False:
                Voar = True
        
        

    pygame.display.update()
	
Arquivo.close()
pygame.quit()

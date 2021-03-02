import sys
import subprocess
import csv
import pygame
from pygame.locals import KEYDOWN,QUIT,K_ESCAPE,K_q,K_1,K_2,K_3,K_4,K_5

def fine():
    pygame.quit()
    sys.exit()

def key_event( mn ):
    for event in pygame.event.get():
        if event.type == QUIT:
            fine()
        elif event.type == KEYDOWN:
            #print('debug=>',event.key)
            if event.key == K_ESCAPE:
                #pygame.display.toggle_fullscreen()
                return
            elif event.key == K_1:
                fname = mn[0][1]
            elif event.key == K_2:
                fname = mn[1][1]
            elif event.key == K_3:
                fname = mn[2][1]
            elif event.key == K_4:
                fname = mn[3][1]
            elif event.key == K_5:
                fname = mn[4][1]            
            elif event.key == K_q:
                fine()
            else:
                return
            subprocess.run( ['sh','./'+fname])
        else:
            pass

def finput():
    with open('./menu.csv', 'r', encoding='utf-8') as f:
        reader = csv.reader(f)
        l = [row for row in reader]
    return l
        
def gen_menu(items, HEIGHT):
    #print('debug=>',pygame.font.get_fonts())
    COLOR1 = (10,10,10)
    sk = HEIGHT//len(items)
    menu = []
    font = pygame.font.SysFont("ヒラキノ角コシックw1", 16)
    for i, t in enumerate(items):
        text = font.render(t[0], True, COLOR1)
        tr   = text.get_rect()
        tr.left = surface.get_rect().left + 10
        tr.top  = surface.get_rect().top + sk * i + 8
        menu.append([text, tr])
    return menu

if __name__=='__main__':
    pygame.init()
    WIDTH=800
    HEIGHT=400
    surface=pygame.display.set_mode( (WIDTH, HEIGHT) )
    clock = pygame.time.Clock()
    FPS=10
    items = finput()
    menu = gen_menu(items, HEIGHT)
    while True:
        surface.fill( (255,255,255) )
        key_event( items )
        for m in menu:
            surface.blit( m[0], m[1] )
        pygame.display.update()
        clock.tick( FPS )
    fine()        

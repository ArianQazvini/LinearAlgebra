from utils import *
import numpy as np
import math
from numpy.linalg import inv

def warpPerspective(img, transform_matrix, output_width, output_height):
    m,n,l=img.shape
    temp=np.zeros((m,n,l))
    for i in range(245,620):
        for j in range(17,994):
                matrixA = np.matrix([[i], [j], [1]])
                matrixMultiplied = np.asarray(transform_matrix * matrixA)
                mappedMatrix = np.asarray([matrixMultiplied[0] / matrixMultiplied[2], matrixMultiplied[1] / matrixMultiplied[2]])
                x = round(mappedMatrix[0][0])
                y = round(mappedMatrix[1][0])
                if (x >= 750):
                    x = 749
                if (y >= 1000):
                    y = 999
                temp[x][y] = img[i][j]

    return temp


def grayScaledFilter(img):

    grayMatrix= np.matrix([[0.299,0.587,0.114],
                           [0.299,0.587,0.114],
                           [0.299,0.587,0.114]])
    return Filter(img,grayMatrix)

def crazyMatrix():
    crazyMat= np.matrix([[0,0,1],
                         [0,0.5,0],
                         [0.5,0.5,0]])
    return crazyMat


def crazyFilter(img):

    return Filter(img,crazyMatrix())

def invertedCrazyFilter(img,crazyMat):
    inverted=inv(crazyMat)
    return Filter(img,inverted)

def scaleImg(img, scale_width, scale_height):

    m,n,l=img.shape
    tempM= int(scale_width*m)
    tempN= int(scale_height*n)
    temp=np.zeros((tempM,tempN,l))
    for i in range(tempM):
        for j in range(tempN):
            newx= i* (1/scale_width)
            newy= j* (1/scale_height)
            x=round(newx)
            y=round(newy)
            if(x>=750):
               x=749
            if(y>=1000):
               y=999
            temp[i][j]=img[x][y]
    return temp

def permuteFilter(img):

    permuteMat=np.matrix([[0,0,1],
                          [0,1,0],
                          [1,0,0]])
    return Filter(img,permuteMat)

if __name__ == "__main__":
    image_matrix = get_input('pic.jpg')
    width, height = 750, 1000
  #  showImage(image_matrix, title="Input Image")
    pts1 = np.float32([[245, 17], [595, 185], [251, 993], [619, 901]])
    pts2 = np.float32([[0, 0], [749, 0], [0, 999], [749, 999]])
    m = getPerspectiveTransform(pts1, pts2)
    warpedImage = warpPerspective(image_matrix, m, width, height)
    showWarpPerspective(warpedImage)

   # grayScaledPic = grayScaledFilter(warpedImage)
   # showImage(grayScaledPic, title="Gray Scaled")

    # crazyImage= crazyFilter(warpedImage)
    #showImage(crazyImage, title="Crazy Filter")
    # invertedCrazyImage=invertedCrazyFilter(crazyImage,crazyMatrix())
    # showImage(invertedCrazyImage, title="Inverted Crazy Filter")

    #scaledImage = scaleImg(warpedImage, 2, 3)
   # showImage(scaledImage, title="Scaled Image")
    permuteImage = permuteFilter(warpedImage)
    showImage(permuteImage, title="Permuted Image")

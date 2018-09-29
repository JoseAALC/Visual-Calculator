# Visual-Calculator
Project done in context of a computer vision course in a Masters degree that.

## Details
  For this project was supposed to take a photo of a simple math expression in blue paper and get the result calculated by the program.
  With that objective we use a very small dataset with the numbers from 0-9 and the operators +,-, x and / each symbol photo was taken by the same mobile phone with in average the same condictions of luminusity and perspective. For this project we implement the computer vision algoriths without the use of any extern tool with the objective of better understanding some basic computer vision techniques.

## Techniques
  * threshold segmentation: We defined a threshold to separate the symbold from the backgroud as the symbols are blue a colour which is not present in backgroud, the treshold was obtained essencially by exprimentation.
  * filter noise: we use a median filter with an windows 3x3 for reduzing the noise, with that reduzing the number of false objects finded from 1267 to 331 with is a 73% reduction.
  * filter objects: after we get the objects from thresholding we remove any object that cannot be a symbol, as very small ones or lateral shadows that can pass by the treshold.
  * resize: after we get the objects we resize the objects to be the same size as our dataset images for that we use interpolation.
  
  * correlation: After that we use a correlation as way of classyfing by comparating our image x with any image $y$ in our dataset where $correlation(x,y) = \sum_{x_{i,j} \in x,y_{i,j} \in y} (x_{i,j} \land y_{i,j}) \lor (\lnot x_{i,j} \land \lnot y_{i,j}) $ where $x_{i,j}$ is a pixel in the image x and $y_{i,j} $ is a pixel in the image y, x is classified as the symbol with greatest correlation with the symbol in x.
  
 ## work limitations
 
  * Dataset: this work uses a very small dataset with only one image for symbol making it very dependent of having the same luminosity and perspective that as in our dataset images.
  * Our work don't explore very well ways of deal with the shadows.
  * Our testset was very small to antecipate any other problems
  * This work is very dependent of a constant threshold to segment the image
  * The accuracy of our classification method is not very high and with a given testset we are not cannot acuractly measure accuracy
  
## possible improvments
  * Take more photos with variations of our symbols to our train to improve the accuracy of our method
  * Incresses our number od tests to better measure the performance of our classifyr
  * Trying other methods to classifing as for example SVM or Decision trees.
  * Study other ways of dealing with shadows
  
 
 
  

from pyspark.sql import SparkSession
from pyspark.ml.regression import LinearRegression
from pyspark.ml.feature import VectorAssembler
# Create spark session
spark = SparkSession.builder.appName("ICP14").getOrCreate()
spark.sparkContext.setLogLevel("ERROR")
# Load data and select feature and label columns
data = spark.read.format("csv").option("header", True).option("inferSchema", True).option("delimiter", ",").load("C:/Users/anees/Downloads/BDP/imports-85.data")
data = data.withColumnRenamed("symboling", "label").select("label", "length", "width", "height")
# Create vector assembler for feature columns
assembler = VectorAssembler(inputCols=data.columns[1:], outputCol="features")
data = assembler.transform(data)
data.show()
lr = LinearRegression(maxIter=10, regParam=0.3, elasticNetParam=0.8)
# Fit the model
model = lr.fit(data)
# Print the coefficients and intercept for linear regression
print("Coefficients: %s" % str(model.coefficients))
print("Intercept: %s" % str(model.intercept))
# Summarize the model over the training set and print out some metrics
trainingSummary = model.summary
print("numIterations: %d" % trainingSummary.totalIterations)
print("objectiveHistory: %s" % str(trainingSummary.objectiveHistory))
trainingSummary.residuals.show()
print("RMSE: %f" % trainingSummary.rootMeanSquaredError)
print("r2: %f" % trainingSummary.r2)
from pyspark.sql import SparkSession
from pyspark.ml.clustering import KMeans
from pyspark.ml.feature import VectorAssembler
# Create spark session
spark = SparkSession.builder.appName("ICP 14").getOrCreate()
spark.sparkContext.setLogLevel("ERROR")
# Load data and select feature and label columns
data = spark.read.format("csv").option("header", True).option("inferSchema", True).option("delimiter", ",").load(
    "C:/Users/anees/Downloads/BDP/diabetic_data.csv")
data = data.select("admission_type_id", "discharge_disposition_id", "admission_source_id", "time_in_hospital",
                   "num_lab_procedures")
# Create vector assembler for feature columns
assembler = VectorAssembler(inputCols=data.columns, outputCol="features")
data = assembler.transform(data)
data.show()
# Trains a k-means model.
kmeans = KMeans().setK(2).setSeed(1)
model = kmeans.fit(data)
# Make predictions
predictions = model.transform(data)
# Shows the result.
centers = model.clusterCenters()
print("Cluster Centers: ")
for center in centers:
    print(center)